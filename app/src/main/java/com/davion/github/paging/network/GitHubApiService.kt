package com.davion.github.paging.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.HashMap

private const val BASE_URL = "https://api.github.com"

interface GitHubApiService {
    @GET("/users")
    fun getUsersAsync(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Deferred<Response<List<User>>>

    @GET(value = "/search/repositories?q=Android")
    fun getAndroidReposAsync(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Deferred<Response<SearchReposResponse>>
}

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object GitHubApi {
    val retrofitService: GitHubApiService by lazy {
        retrofit.create(GitHubApiService::class.java)
    }
}