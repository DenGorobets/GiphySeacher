package com.den.gorobets.giphyseacher.utils

import android.os.Build.VERSION.SDK_INT
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.den.gorobets.giphyseacher.R
import com.den.gorobets.giphyseacher.databinding.FragmentSearchBinding

fun View.customVisible(bool: Boolean) {
    if (bool) this.visibility = View.VISIBLE else this.visibility = View.GONE
}

fun ImageView.loadImage(url: String?) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    load(url, imageLoader) {
        crossfade(600)
        placeholder(R.drawable.loading)
        error(R.drawable.image_broken)
    }
}

fun FragmentSearchBinding.updateGamesFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
    searchGiphyView.query.trim().let {
        if (it.isNotEmpty()) {
            searchGiphyListRecyclerView.scrollToPosition(0)
            onQueryChanged(UiAction.Search(query = it.toString()))
        }
    }
}