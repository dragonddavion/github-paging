package com.davion.github.paging.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davion.github.paging.databinding.ItemRepoBinding
import com.davion.github.paging.network.Repo

class ReposAdapter : PagingDataAdapter<Repo, RepoViewHolder>(RepoDiffUtil()) {
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let {
            holder.bind(repo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.from(parent)
    }
}

class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(repo: Repo) {
        binding.repo = repo
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup) : RepoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemRepoBinding.inflate(inflater)
            return RepoViewHolder(binding)
        }
    }
}

class RepoDiffUtil : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}