package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GiphySearchListDTO(
    val data: List<Datum>,
    val pagination: Pagination,
    val meta: Meta
)