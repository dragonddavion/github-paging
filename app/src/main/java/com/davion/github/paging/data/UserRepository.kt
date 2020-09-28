package com.davion.github.paging.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davion.github.paging.network.GitHubApi
import com.davion.github.paging.network.GitHubApiService
import com.davion.github.paging.network.Repo
import com.davion.github.paging.network.User
import com.davion.github.paging.ui.repo.RepoPagingSource
import kotlinx.coroutines.flow.Flow

private const val NETWORK_PAGE_SIZE = 30

class UserRepository {

    var since: Int = 0
    var page: Int = 1

    suspend fun getUsers(): List<User> {
        val response = GitHubApi.retrofitService.getUsersAsync(since, NETWORK_PAGE_SIZE).await()

        return if (response.isSuccessful) {
            Log.d("Davion1", "since: $since, size: $NETWORK_PAGE_SIZE")
            if (response.body() != null) {
                since = response.body()!![29].id.toInt()
                response.body()!!
            } else {
                listOf()
            }
        } else {
            Log.d("Davion2", "since: $since, size: $NETWORK_PAGE_SIZE")
            listOf()
        }
    }

     fun getRepos() : Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoPagingSource(service = GitHubApi.retrofitService)
            }
        ).flow
    }
}