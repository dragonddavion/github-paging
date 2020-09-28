package com.davion.github.paging.ui.repo

import androidx.paging.PagingSource
import com.davion.github.paging.network.GitHubApiService
import com.davion.github.paging.network.Repo
import retrofit2.HttpException
import java.io.IOException

class RepoPagingSource(private val service: GitHubApiService) : PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: 1
        return try {
            val response = service.getAndroidReposAsync(position, params.loadSize).await()

            val repos: List<Repo> = if (response.body() != null) {
                response.body()!!.listRepo
            } else {
                listOf()
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}