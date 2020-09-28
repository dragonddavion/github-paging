package com.davion.github.paging.network

import com.squareup.moshi.Json

data class User (
    val id: String,
    val login: String,
    @Json(name = "site_admin") val siteAdmin: Boolean
)