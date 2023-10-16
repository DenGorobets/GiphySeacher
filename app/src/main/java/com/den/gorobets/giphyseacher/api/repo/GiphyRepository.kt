package com.den.gorobets.giphyseacher.api.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.den.gorobets.giphyseacher.api.ApiHelper
import com.den.gorobets.giphyseacher.api.dto.Datum
import com.den.gorobets.giphyseacher.api.paging.GiphyListPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val api: ApiHelper
) {

    fun receiveSearchedList(game: String): Flow<PagingData<Datum>> =
        Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GiphyListPagingSource(api, game) }
        ).flow
}