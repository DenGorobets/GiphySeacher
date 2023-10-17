package com.den.gorobets.giphyseacher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.den.gorobets.giphyseacher.databinding.FragmentDetailsBinding
import com.den.gorobets.giphyseacher.utils.loadImage
import com.den.gorobets.giphyseacher.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding by lazy { FragmentDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupReceivedGif()
    }

    private fun setupReceivedGif() {
        viewModel.giphyData.onEach { giphyUrl ->
            binding.giphyImage.loadImage(giphyUrl)
        }.launchIn(lifecycleScope)

        val argument = arguments?.getString("giphy_url")
        viewModel.setGiphyUrl(argument.orEmpty())
    }
}