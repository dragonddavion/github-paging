package com.davion.github.paging.model

sealed class UIModel {
    data class RepoItem(val repo: Repo) : UIModel()
    data class SeparatorItem(val description: String) : UIModel()
}

val UIModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000