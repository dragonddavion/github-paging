package com.davion.github.paging.ui.users

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davion.github.paging.UserRepository
import com.davion.github.paging.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel @ViewModelInject constructor(private val repository: UserRepository): ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val allUser = mutableListOf<User>()

    private val _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>>
        get() = _users

    private var gettingData = false

    fun getNextUserPage(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (gettingData) {
            return
        }
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            gettingData = true
            val startTime = System.currentTimeMillis()
            viewModelScope.launch {
                Log.d("Davion before ${System.currentTimeMillis() - startTime}", Thread.currentThread().name)
                withContext(Dispatchers.IO) {
                    val nextUsers = repository.getUsers()
                    allUser.addAll(nextUsers)
                    _users.postValue(allUser)
                    gettingData = false
                }
                Log.d("Davion after ${System.currentTimeMillis() - startTime}", Thread.currentThread().name)
            }
        }
    }

    fun getFirstPage() {
        Log.d("Davion", "get First Page")
        viewModelScope.launch {
            val nextUsers = repository.getUsers()
            allUser.addAll(nextUsers)
            _users.value = allUser
        }
    }
}