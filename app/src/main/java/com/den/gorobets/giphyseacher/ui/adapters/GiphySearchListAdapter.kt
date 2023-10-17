package com.den.gorobets.giphyseacher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.den.gorobets.giphyseacher.databinding.GiphyRecyclerItemBinding
import com.den.gorobets.giphyseacher.model.dto.Datum
import com.den.gorobets.giphyseacher.utils.loadImage

class GiphySearchListAdapter(
    private val onCardClick: (String?) -> Unit
) : PagingDataAdapter<Datum, GiphySearchListAdapter.GameDataViewHolder>(ListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDataViewHolder {
        val binding =
            GiphyRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameDataViewHolder, position: Int) {
        val data = getItem(position)

        data?.let {
            holder.bindData(it)
        }
    }

    inner class GameDataViewHolder(
        private val binding: GiphyRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Datum) {

            binding.apply {
                with(data) {
                    giphyCardImage.loadImage(images.fixedWidthDownsampled.url)
                    profileCardName.text = user?.displayName

                    root.setOnClickListener {
                        onCardClick.invoke(images.original.url)
                    }
                }
            }
        }
    }
}