package com.den.gorobets.giphyseacher.model.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Onclick(
    val url: String? = null
)