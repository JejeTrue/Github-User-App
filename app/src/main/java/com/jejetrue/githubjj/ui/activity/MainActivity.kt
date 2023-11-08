package com.jejetrue.githubjj.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jejetrue.githubjj.R
import com.jejetrue.githubjj.databinding.ActivityMainBinding
import com.jejetrue.githubjj.viewmodel.MainViewModel
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jejetrue.githubjj.data.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        setUserData()
        searchUser()

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

    }
    private fun setUserData(){
        viewModel.getSearchList.observe(this){users->
            val adapter = UserAdapter()
            adapter.submitList(users)
            binding.rvUsers.adapter = adapter
        }
    }

    private fun searchUser(){
        with(binding){

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, i, keyEvent ->

                    searchBar.text = searchView.text
                    val user = searchBar.text.toString()

                    searchView.hide()
                    viewModel.searchUser(user)
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}