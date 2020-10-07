package com.davion.github.paging.model

import com.squareup.moshi.Json

data class User (
    val id: Int,
    val login: String,
    @Json(name = "site_admin") val siteAdmin: Boolean,
    @Json(name = "avatar_url") val avatarUrl: String?
)