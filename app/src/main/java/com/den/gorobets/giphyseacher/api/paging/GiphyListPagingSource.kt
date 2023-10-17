package com.den.gorobets.giphyseacher.api.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.den.gorobets.giphyseacher.api.ApiHelper
import com.den.gorobets.giphyseacher.model.dto.Datum
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

class GiphyListPagingSource(
    private val api: ApiHelper,
    private val query: String = ""
) : PagingSource<Int, Datum>() {

    override fun getRefreshKey(state: PagingState<Int, Datum>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Datum> {
        val position = params.key ?: 1
        val apiQuery = query

        return try {
            val response =
                api.receiveGiphySearchedList(
                    query = apiQuery,
                    page = position,
                    pageSize = params.loadSize
                )
            val repos = response?.data ?: emptyList()
            val nextKey = if (repos.isEmpty()) {
                null
            } else position + (params.loadSize / NETWORK_PAGE_SIZE)

            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: RedirectResponseException) {
            LoadResult.Error(e)
        } catch (e: ClientRequestException) {
            LoadResult.Error(e)
        } catch (e: ServerResponseException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 25
    }
}
