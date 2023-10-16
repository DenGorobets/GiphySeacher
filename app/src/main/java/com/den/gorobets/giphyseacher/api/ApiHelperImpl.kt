package com.den.gorobets.giphyseacher.api

import com.den.gorobets.giphyseacher.api.dto.GiphySearchListDTO
import com.den.gorobets.giphyseacher.api.http_engine.EngineService
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val engineService: EngineService
) : ApiHelper {

    override suspend fun receiveGiphySearchedList(
        query: String,
        page: Int,
        pageSize: Int
    ): GiphySearchListDTO? =
        engineService.receiveGameSearchData(query, page, pageSize)
}
