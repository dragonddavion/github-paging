package com.davion.github.paging.ui.repo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.davion.github.paging.R
import com.davion.github.paging.data.UserRepository
import com.davion.github.paging.databinding.FragmentReposBinding
import kotlinx.coroutines.launch

class ReposFragment : Fragment() {
    private lateinit var binding: FragmentReposBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)
        
        return binding.root
    }
}