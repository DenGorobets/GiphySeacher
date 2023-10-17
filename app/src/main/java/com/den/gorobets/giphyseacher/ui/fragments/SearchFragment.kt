package com.den.gorobets.giphyseacher.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.den.gorobets.giphyseacher.R
import com.den.gorobets.giphyseacher.databinding.FragmentSearchBinding
import com.den.gorobets.giphyseacher.model.dto.Datum
import com.den.gorobets.giphyseacher.ui.adapters.GiphyLoadStateAdapter
import com.den.gorobets.giphyseacher.ui.adapters.GiphySearchListAdapter
import com.den.gorobets.giphyseacher.utils.UiAction
import com.den.gorobets.giphyseacher.utils.UiState
import com.den.gorobets.giphyseacher.utils.customVisible
import com.den.gorobets.giphyseacher.utils.updateGamesFromInput
import com.den.gorobets.giphyseacher.viewmodel.GiphyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<GiphyViewModel>()
    private val gifAdapter by lazy {
        GiphySearchListAdapter { id ->
            openGameDetails(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindMenuChangeLayoutButton()

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun openGameDetails(gifUrl: String?) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(gifUrl)

        findNavController().navigate(action)
    }

    private fun FragmentSearchBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Datum>>,
        uiActions: (UiAction) -> Unit
    ) {

        val header = GiphyLoadStateAdapter { gifAdapter.retry() }

        viewModel.recyclerLayout.onEach { type ->
            val rowsCount = when (type) {
                0 -> 1
                1 -> 2
                else -> 1
            }

            searchGiphyListRecyclerView.layoutManager = StaggeredGridLayoutManager(
                rowsCount, VERTICAL
            )

        }.launchIn(lifecycleScope)

        searchGiphyListRecyclerView.adapter = gifAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = GiphyLoadStateAdapter { gifAdapter.retry() })

        bindSearch(uiState, onQueryChanged = uiActions)
        bindList(
            repoAdapter = gifAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentSearchBinding.bindList(
        repoAdapter: GiphySearchListAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Datum>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {

        searchGiphyListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        refreshButton.setOnClickListener { repoAdapter.retry() }

        val notLoading = repoAdapter.loadStateFlow.distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch =
            uiState.map { it.hasNotScrolledForCurrentSearch }.distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading, hasNotScrolledForCurrentSearch, Boolean::and
        ).distinctUntilChanged()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listOf(async {
                    pagingData.collectLatest(repoAdapter::submitData)
                }, async {
                    shouldScrollToTop.collect { shouldScroll ->
                        if (shouldScroll) searchGiphyListRecyclerView.scrollToPosition(0)
                    }
                }, async {
                    repoAdapter.loadStateFlow.collect { loadState ->

                        val isListEmpty =
                            loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0

                        connectionTextView.customVisible(isListEmpty)
                        searchGiphyListRecyclerView.customVisible(!isListEmpty)
                        viewModel.recyclerLayout.onEach { type ->
                            when (type) {
                                0 -> linearAnimationView.customVisible(loadState.source.refresh is LoadState.Loading)
                                1 -> gridAnimationView.customVisible(loadState.source.refresh is LoadState.Loading)
                            }
                        }.launchIn(lifecycleScope)
                        refreshButton.customVisible(loadState.source.refresh is LoadState.Error)

                        val errorState = loadState.source.append as? LoadState.Error
                            ?: loadState.source.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.prepend as? LoadState.Error
                        errorState?.let {
                            Toast.makeText(
                                requireContext(), "Sorry, ${it.error}", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }
        }
    }

    private fun FragmentSearchBinding.bindSearch(
        uiState: StateFlow<UiState>, onQueryChanged: (UiAction.Search) -> Unit
    ) {

        searchGiphyView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (!query.isNullOrBlank()) {
                    updateGamesFromInput(onQueryChanged)
                    searchGiphyView.clearFocus()
                    true
                } else false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            uiState.map { it.query }.distinctUntilChanged().collect { query ->
                searchGiphyView.setQuery(query, false)
            }
        }

        searchGiphyView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateGamesFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
    }

    private fun bindMenuChangeLayoutButton() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.change_layout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {

                    R.id.menu_linear_layout -> {
                        viewModel.setLayoutType(0)
                        true
                    }

                    R.id.menu_grid_layout -> {
                        viewModel.setLayoutType(1)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}