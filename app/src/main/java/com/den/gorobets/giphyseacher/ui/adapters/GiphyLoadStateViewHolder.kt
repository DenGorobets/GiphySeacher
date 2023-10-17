package com.den.gorobets.giphyseacher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.den.gorobets.giphyseacher.R
import com.den.gorobets.giphyseacher.databinding.LayoutLoadStateFooterBinding
import com.den.gorobets.giphyseacher.utils.customVisible

class GiphyLoadStateViewHolder(
    private val binding: LayoutLoadStateFooterBinding,
    retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retryCallback.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                errorTextView.text = loadState.error.localizedMessage
            }
            loadAnimationView.customVisible(loadState is LoadState.Loading)
            retryButton.customVisible(loadState is LoadState.Error)
            errorTextView.customVisible(loadState is LoadState.Error)
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GiphyLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_load_state_footer, parent, false)
            val binding = LayoutLoadStateFooterBinding.bind(view)
            return GiphyLoadStateViewHolder(binding, retry)
        }
    }
}