package com.davion.github.paging.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davion.github.paging.data.UserRepository
import com.davion.github.paging.network.Repo
import kotlinx.coroutines.flow.Flow

class RepoViewModel : ViewModel() {

    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun getRepositories(): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Repo>> = UserRepository().getRepos().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}