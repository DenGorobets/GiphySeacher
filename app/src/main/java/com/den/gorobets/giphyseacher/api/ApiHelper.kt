package com.den.gorobets.giphyseacher.api

import com.den.gorobets.giphyseacher.model.dto.GiphySearchListDTO


interface ApiHelper {
    suspend fun receiveGiphySearchedList(
        query: String,
        page: Int,
        pageSize: Int
    ): GiphySearchListDTO?
}
