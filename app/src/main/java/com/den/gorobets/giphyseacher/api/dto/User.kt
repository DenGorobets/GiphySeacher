package com.den.gorobets.giphyseacher.api.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class User(
    @SerialName("avatar_url")
    val avatarURL: String? = null,

    @SerialName("banner_image")
    val bannerImage: String? = null,

    @SerialName("banner_url")
    val bannerURL: String? = null,

    @SerialName("profile_url")
    val profileURL: String? = null,

    val username: String? = null,

    @SerialName("display_name")
    val displayName: String? = null,

    val description: String? = null,

    @SerialName("instagram_url")
    val instagramURL: String? = null,

    @SerialName("website_url")
    val websiteURL: String? = null,

    @SerialName("is_verified")
    val isVerified: Boolean
)
