package com.den.gorobets.giphyseacher.model.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Images(
    val original: FixedHeight,

    @SerialName("fixed_height")
    val fixedHeight: FixedHeight,

    @SerialName("fixed_height_downsampled")
    val fixedHeightDownsampled: FixedHeight,

    @SerialName("fixed_height_small")
    val fixedHeightSmall: FixedHeight,

    @SerialName("fixed_width")
    val fixedWidth: FixedHeight,

    @SerialName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedHeight,

    @SerialName("fixed_width_small")
    val fixedWidthSmall: FixedHeight
)
