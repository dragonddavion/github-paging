package com.davion.github.paging

import com.davion.github.paging.network.GitHubApi
import com.davion.github.paging.network.User

private const val NETWORK_PAGE_SIZE = 30

class UserRepository {

    var since: Int = 0

    suspend fun getUsers(): List<User> {
        val response = GitHubApi.retrofitService.getUsersAsync(since, NETWORK_PAGE_SIZE).await()

        return if (response.isSuccessful) {
            since += NETWORK_PAGE_SIZE
            response.body()!!
        } else {
            listOf()
        }
    }
}