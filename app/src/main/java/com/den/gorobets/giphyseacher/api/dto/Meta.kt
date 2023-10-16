package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Meta(
    val status: Long,
    val msg: String? = null,

    @SerialName("response_id")
    val responseID: String? = null
)
