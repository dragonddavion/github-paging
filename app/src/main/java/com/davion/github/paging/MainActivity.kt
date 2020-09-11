package com.davion.github.paging

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davion.github.paging.databinding.ActivityMainBinding
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Davion", "onCreate activity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = userViewModel

        initRecyclerView()
        userViewModel.getFirstPage()
    }

    private fun initRecyclerView() {
        val adapter = UserAdapter()
        binding.listUser.adapter = adapter
        binding.listUser.layoutManager = LinearLayoutManager(this)

        setScrollListener()

        userViewModel.users.observe(this, {
            //Log.d("Davion", "users: $it")
            adapter.submitList(ArrayList(it))
        })
    }

    private fun setScrollListener() {
        val layoutManager = binding.listUser.layoutManager as LinearLayoutManager
        binding.listUser.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                userViewModel.getNextUserPage(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}