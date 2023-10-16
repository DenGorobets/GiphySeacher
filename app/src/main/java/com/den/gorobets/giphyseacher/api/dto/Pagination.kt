package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Pagination(
    @SerialName("total_count")
    val totalCount: Long,

    val count: Long,
    val offset: Long
)
