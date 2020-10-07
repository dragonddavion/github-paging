package com.davion.github.paging.ui.repo

import com.davion.github.paging.network.Repo

sealed class UIModel {
    data class RepoItem(val repo: Repo) : UIModel()
    data class SeparatorItem(val description: String) : UIModel()
}

val UIModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000