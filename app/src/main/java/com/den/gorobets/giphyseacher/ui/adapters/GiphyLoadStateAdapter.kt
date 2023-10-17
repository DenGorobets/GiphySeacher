package com.den.gorobets.giphyseacher.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GiphyLoadStateAdapter(private val retryCallback: () -> Unit) :
    LoadStateAdapter<GiphyLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: GiphyLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): GiphyLoadStateViewHolder {
        return GiphyLoadStateViewHolder.create(parent, retryCallback)
    }
}