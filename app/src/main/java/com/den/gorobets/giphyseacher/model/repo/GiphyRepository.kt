package com.den.gorobets.giphyseacher.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.den.gorobets.giphyseacher.api.ApiHelper
import com.den.gorobets.giphyseacher.api.paging.GiphyListPagingSource
import com.den.gorobets.giphyseacher.api.paging.GiphyListPagingSource.Companion.NETWORK_PAGE_SIZE
import com.den.gorobets.giphyseacher.model.dto.Datum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiphyRepository @Inject constructor(
    private val api: ApiHelper
) {

    fun receiveSearchedList(game: String): Flow<PagingData<Datum>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GiphyListPagingSource(api, game) }
        ).flow
}