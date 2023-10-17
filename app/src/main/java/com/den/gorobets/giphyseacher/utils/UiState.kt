package com.den.gorobets.giphyseacher.utils

import com.den.gorobets.giphyseacher.viewmodel.GiphyViewModel.Companion.DEFAULT_QUERY

data class UiState(

    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)