package com.den.gorobets.giphyseacher.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.den.gorobets.giphyseacher.model.dto.Datum
import com.den.gorobets.giphyseacher.model.repo.GiphyRepository
import com.den.gorobets.giphyseacher.utils.UiAction
import com.den.gorobets.giphyseacher.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val repository: GiphyRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private var _state: StateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state

    val pagingDataFlow: Flow<PagingData<Datum>>
    val accept: (UiAction) -> Unit

    private val _recyclerLayout = MutableStateFlow(0)
    val recyclerLayout: StateFlow<Int> = _recyclerLayout

    init {
        val initialQuery: String = stateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
        val lastQueryScrolled: String =
            stateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart {
                emit(UiAction.Search(query = initialQuery))
            }
        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(lastQueryScrolled)) }

        pagingDataFlow = searches
            .flatMapLatest { getGameSearchList(query = (it.query)) }
            .cachedIn(viewModelScope)

        _state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState()
        )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    fun setLayoutType(type: Int) {
        _recyclerLayout.value = type
    }

    private fun getGameSearchList(query: String = "") =
        repository.receiveSearchedList(query)

    override fun onCleared() {
        stateHandle[LAST_SEARCH_QUERY] = state.value.query
        stateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
        super.onCleared()
    }

    companion object {
        const val LAST_SEARCH_QUERY: String = "last_search_query"
        const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
        const val DEFAULT_QUERY: String = ""
    }
}