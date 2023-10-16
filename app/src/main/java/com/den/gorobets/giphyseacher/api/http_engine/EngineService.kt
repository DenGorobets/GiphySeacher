package com.den.gorobets.giphyseacher.api.http_engine

import com.den.gorobets.giphyseacher.model.dto.GiphySearchListDTO

interface EngineService {
    suspend fun receiveGameSearchData(query: String, page: Int, pageSize: Int): GiphySearchListDTO?
}