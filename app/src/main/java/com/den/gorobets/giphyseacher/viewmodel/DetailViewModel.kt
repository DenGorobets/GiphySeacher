package com.den.gorobets.giphyseacher.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private val _giphyData = MutableStateFlow<String?>(null)
    val giphyData: StateFlow<String?> = _giphyData

    fun setGiphyUrl(url: String) {
        _giphyData.value = url
    }
}