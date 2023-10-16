package com.den.gorobets.giphyseacher.api.http_engine

import com.den.gorobets.giphyseacher.GiphyApplication.Companion.DEFAULT_LANGUAGE
import com.den.gorobets.giphyseacher.model.dto.GiphySearchListDTO
import javax.inject.Inject

class EngineServiceImpl @Inject constructor(
    private val checker: EngineChecker
) : EngineService {

    override suspend fun receiveGameSearchData(
        query: String,
        page: Int,
        pageSize: Int
    ): GiphySearchListDTO? {

        val parameters = mapOf(
            "q" to query,
            "offset" to page,
            "limit" to pageSize,
            "rating" to "g",
            "lang" to DEFAULT_LANGUAGE,
            "bundle" to "messaging_non_clips",
        )
        return checker.performHttpGet(
            API_PATH,
            parameters,
            "Failed to receive server data"
        )
    }
}