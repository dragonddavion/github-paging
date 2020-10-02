package com.davion.github.paging.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davion.github.paging.R
import com.davion.github.paging.databinding.FragmentUsersBinding


class UsersFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)
        binding.viewModel = viewModel

        initRecyclerView()
        viewModel.getFirstPage()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = UserAdapter()
        binding.listUser.adapter = adapter
        binding.listUser.layoutManager = LinearLayoutManager(context)

        setScrollListener()

        viewModel.users.observe(viewLifecycleOwner, {
            //Log.d("Davion", "users: $it")
            adapter.submitList(ArrayList(it))
        })

        val decoration = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        binding.listUser.addItemDecoration(decoration)
    }

    private fun setScrollListener() {
        val layoutManager = binding.listUser.layoutManager as LinearLayoutManager
        binding.listUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.getNextUserPage(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}