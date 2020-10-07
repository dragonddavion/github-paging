package com.davion.github.paging.model

import com.squareup.moshi.Json

data class Repo (
    val id: Int,
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "stargazers_count")
    val stars: Int,
    val description: String?,
    val fork: Boolean,
    val language: String?,
    val owner: Owner
)

data class Owner (
    val id: Int,
    val login: String
)