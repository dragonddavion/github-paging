package com.davion.github.paging.network

import com.davion.github.paging.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("/users")
    fun getUsersAsync(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Deferred<Response<List<User>>>

    @GET(value = "/search/repositories?sort=stars&q=Androidin:name,description")
    fun getAndroidReposAsync(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Deferred<Response<SearchReposResponse>>
}