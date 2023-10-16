package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Datum(
    val type: String? = null,
    val id: String? = null,
    val url: String? = null,
    val slug: String? = null,

    @SerialName("bitly_gif_url")
    val bitlyGIFURL: String? = null,

    @SerialName("bitly_url")
    val bitlyURL: String? = null,

    @SerialName("embed_url")
    val embedURL: String? = null,

    val username: String? = null,
    val source: String? = null,
    val title: String? = null,
    val rating: String? = null,

    @SerialName("content_url")
    val contentURL: String? = null,

    @SerialName("source_tld")
    val sourceTLD: String? = null,

    @SerialName("source_post_url")
    val sourcePostURL: String? = null,

    @SerialName("is_sticker")
    val isSticker: Long,

    @SerialName("import_datetime")
    val importDatetime: String? = null,

    @SerialName("trending_datetime")
    val trendingDatetime: String? = null,

    val images: Images,
    val user: User? = null,

    @SerialName("analytics_response_payload")
    val analyticsResponsePayload: String? = null,

    val analytics: Analytics
)
