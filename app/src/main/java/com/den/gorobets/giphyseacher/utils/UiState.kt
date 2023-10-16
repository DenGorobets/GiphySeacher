package com.den.gorobets.giphyseacher.utils

const val DEFAULT_QUERY: String = ""

data class UiState(

    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)