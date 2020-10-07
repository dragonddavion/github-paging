package com.davion.github.paging.ui.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davion.github.paging.R
import com.davion.github.paging.databinding.ItemRepoBinding
import com.davion.github.paging.databinding.ReposLoadStateFooterViewItemBinding
import com.davion.github.paging.network.Repo
import java.lang.UnsupportedOperationException

class ReposAdapter : PagingDataAdapter<UIModel, RecyclerView.ViewHolder>(RepoDiffUtil()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is UIModel.RepoItem -> (holder as RepoViewHolder).bind(uiModel.repo)
                is UIModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(uiModel.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_repo) {
            RepoViewHolder.from(parent)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UIModel.RepoItem -> R.layout.item_repo
            is UIModel.SeparatorItem -> R.layout.item_separator
            null -> throw UnsupportedOperationException("Unknown View")
        }
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
            val binding = DataBindingUtil.inflate<ItemRepoBinding>(inflater, R.layout.item_repo, parent, false)
            return RepoViewHolder(binding)
        }
    }
}

class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val description: TextView = view.findViewById(R.id.tv_separator)

    fun bind(separatorText: String) {
        description.text = separatorText
    }

    companion object {
        fun create(parent: ViewGroup) : SeparatorViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_separator, parent, false)
            return SeparatorViewHolder(view)
        }
    }
}

class RepoDiffUtil : DiffUtil.ItemCallback<UIModel>() {
    override fun areItemsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
        return (oldItem is UIModel.RepoItem && newItem is UIModel.RepoItem && oldItem.repo.fullName == newItem.repo.fullName ||
                oldItem is UIModel.SeparatorItem && newItem is UIModel.SeparatorItem && oldItem.description == newItem.description)
    }

    override fun areContentsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
        return oldItem == newItem
    }
}