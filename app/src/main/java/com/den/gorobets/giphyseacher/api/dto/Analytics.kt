package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Analytics(
    val onload: Onclick,
    val onclick: Onclick,
    val onsent: Onclick
)