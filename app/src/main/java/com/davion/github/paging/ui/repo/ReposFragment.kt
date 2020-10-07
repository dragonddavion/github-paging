package com.davion.github.paging.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.davion.github.paging.R
import com.davion.github.paging.databinding.FragmentReposBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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

        initData()

        return binding.root
    }

    private fun initData() {
        repoJob?.cancel()
        repoJob = lifecycleScope.launch {
            viewModel.getRepositories().collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.repositoryList.scrollToPosition(0)
                }
        }

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initRecyclerView() {
        binding.repositoryList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding.repositoryList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.repositoryList.scrollToPosition(0) }
        }
        val decoration =
            DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        binding.repositoryList.addItemDecoration(decoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        repoJob?.cancel()
    }
}


