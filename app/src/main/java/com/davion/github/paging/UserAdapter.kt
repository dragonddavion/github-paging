package com.davion.github.paging


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davion.github.paging.databinding.ItemUserBinding
import com.davion.github.paging.network.User

class UserAdapter (): ListAdapter<User, UserViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.user = user
    }

    companion object {
        fun from(parent: ViewGroup) : UserViewHolder{
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemUserBinding.inflate(inflater)
            return UserViewHolder(binding)
        }
    }
}

class UserDiffCallback(): DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}