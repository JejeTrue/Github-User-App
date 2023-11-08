package com.jejetrue.githubjj.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jejetrue.githubjj.R
import com.jejetrue.githubjj.data.adapter.UserAdapter
import com.jejetrue.githubjj.databinding.FragmentFollowingBinding
import com.jejetrue.githubjj.viewmodel.DetailViewModel


class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private val followingViewModel by viewModels<DetailViewModel>()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        followingViewModel.getFollowing(username)

        followingViewModel.getFollowing.observe(viewLifecycleOwner){
                users->
            val adapter = UserAdapter()
            adapter.submitList(users)
            binding.rvFollowing.adapter = adapter
        }

        followingViewModel.isLoadingFollowing.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
    }
}