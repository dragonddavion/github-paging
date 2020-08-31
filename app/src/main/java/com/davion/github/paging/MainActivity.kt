package com.davion.github.paging

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.davion.github.paging.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(biding.root)

        biding.viewModel = userViewModel

        val adapter = UserAdapter()
        biding.listUser.adapter = adapter
        biding.listUser.layoutManager = LinearLayoutManager(this)

        userViewModel.users.observe(this, {
            Log.d("Davion", "users: $it")
            val list =
            adapter.submitList(ArrayList(it))
        })
    }
}