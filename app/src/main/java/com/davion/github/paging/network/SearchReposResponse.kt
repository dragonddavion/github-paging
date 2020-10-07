package com.davion.github.paging.network

import com.davion.github.paging.model.Repo
import com.squareup.moshi.Json

data class SearchReposResponse (
    @Json(name = "total_count") val totalResult: Int = 0,
    @Json(name = "items") val listRepo: List<Repo> = listOf()
)