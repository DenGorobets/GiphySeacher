package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FixedHeight(
    val height: String? = null,
    val width: String? = null,
    val size: String? = null,
    val url: String? = null,

    @SerialName("mp4_size")
    val mp4Size: String? = null,

    val mp4: String? = null,

    @SerialName("webp_size")
    val webpSize: String? = null,

    val webp: String? = null,
    val frames: String? = null,
    val hash: String? = null
)