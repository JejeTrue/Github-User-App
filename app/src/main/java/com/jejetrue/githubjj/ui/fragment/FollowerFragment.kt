package com.jejetrue.githubjj.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jejetrue.githubjj.R
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jejetrue.githubjj.data.adapter.UserAdapter
import com.jejetrue.githubjj.databinding.FragmentFollowerBinding
import com.jejetrue.githubjj.viewmodel.DetailViewModel


class FollowerFragment : Fragment() {
    private lateinit var  binding: FragmentFollowerBinding
    private val followerViewModel by viewModels<DetailViewModel>()
    private var username: String? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        followerViewModel.getFollower(username)

        followerViewModel.getFollwers.observe(viewLifecycleOwner){
                users->
            val adapter = UserAdapter()
            adapter.submitList(users)
            binding.rvFollowers.adapter = adapter
        }

        followerViewModel.isLoadingFollower.observe(viewLifecycleOwner){
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
    }
}