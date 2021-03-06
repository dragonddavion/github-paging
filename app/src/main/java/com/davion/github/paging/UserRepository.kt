package com.davion.github.paging

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davion.github.paging.network.GithubApiService
import com.davion.github.paging.model.Repo
import com.davion.github.paging.model.User
import com.davion.github.paging.ui.repo.RepoPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val NETWORK_PAGE_SIZE = 50

class UserRepository @Inject constructor(private val retrofitService: GithubApiService) {

    var since: Int = 0
    var page: Int = 1

    suspend fun getUsers(): List<User> {
        try {
            val response = retrofitService.getUsersAsync(since, NETWORK_PAGE_SIZE).await()
            return if (response.isSuccessful) {
                Log.d("Davion1", "since: $since, size: $NETWORK_PAGE_SIZE")
                if (response.body() != null) {
                    since = response.body()!![NETWORK_PAGE_SIZE - 1].id
                    response.body()!!
                } else {
                    listOf()
                }
            } else {
                Log.d("Davion2", "since: $since, size: $NETWORK_PAGE_SIZE")
                listOf()
            }
        } catch (exception: IOException) {
            Log.d("Davion3", "${exception.message}")
            return listOf()
        } catch (exception: HttpException) {
            Log.d("Davion4", exception.message())
            return listOf()
        }
    }

     fun getRepos() : Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoPagingSource(service = retrofitService)
            }
        ).flow
    }
}