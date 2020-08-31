package com.davion.github.paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davion.github.paging.network.User
import kotlinx.coroutines.launch

class UserViewModel(): ViewModel() {
    private val repository: UserRepository = UserRepository()

    private val allUser = mutableListOf<User>()

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>>
        get() = _users

    fun getNextUser() {
        Log.d("Davion", "getNextUser Button clicked")
        viewModelScope.launch {
            val nextUsers = repository.getUsers()
            allUser.addAll(nextUsers)
            _users.value = allUser
        }
    }
}