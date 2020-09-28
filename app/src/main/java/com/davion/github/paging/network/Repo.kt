package com.davion.github.paging.network

data class Repo (
    val id: Int,
    val name: String,
    val description: String?,
    val fork: Boolean
)