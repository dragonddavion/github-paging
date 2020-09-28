package com.davion.github.paging.ui.repo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.davion.github.paging.R
import com.davion.github.paging.data.UserRepository
import com.davion.github.paging.databinding.FragmentReposBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReposFragment : Fragment() {
    private lateinit var binding: FragmentReposBinding

    private val viewModel: RepoViewModel by viewModels()

    private var repoJob: Job? = null

    private val adapter: ReposAdapter by lazy { ReposAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)

        initRecyclerView()

        repoJob?.cancel()
        repoJob = lifecycleScope.launch {
            viewModel.getRepositories().collectLatest {
                adapter.submitData(it)
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        binding.repositoryList.adapter = adapter
        binding.repositoryList.layoutManager = LinearLayoutManager(requireContext())
    }
}