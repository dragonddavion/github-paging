package com.davion.github.paging.ui.repo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.davion.github.paging.UserRepository
import com.davion.github.paging.model.Repo
import com.davion.github.paging.model.UIModel
import com.davion.github.paging.model.roundedStarCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepoViewModel @ViewModelInject constructor(private val repository: UserRepository) : ViewModel() {

    private var currentSearchResult: Flow<PagingData<UIModel>>? = null

    fun getRepositories(): Flow<PagingData<UIModel>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<UIModel>> = repository.getRepos().map { pagingData : PagingData<Repo> ->
            pagingData.map {
                UIModel.RepoItem(it)
            }
        }.map {
            it.insertSeparators<UIModel.RepoItem, UIModel> { before, after ->
                if (after == null) {
                    return@insertSeparators null
                }

                if (before == null) {
                    return@insertSeparators UIModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                }

                if (before.roundedStarCount > after.roundedStarCount) {
                    if (after.roundedStarCount >= 1) {
                        UIModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                    } else {
                        UIModel.SeparatorItem("< 10.000+ stars")
                    }
                } else {
                    null
                }
            }
        }.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}