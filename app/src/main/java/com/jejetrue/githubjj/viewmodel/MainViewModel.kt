package com.jejetrue.githubjj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jejetrue.githubjj.data.response.GithubResponse
import com.jejetrue.githubjj.data.response.ItemsItem
import com.jejetrue.githubjj.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _searchList = MutableLiveData<ArrayList<ItemsItem>>()
    val getSearchList: LiveData<ArrayList<ItemsItem>> = _searchList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUser(USERS)
    }

    fun searchUser(username: String){
        try {
            _isLoading.value = true
            val client = ApiConfig.getApiService().search(username)
            client.enqueue(object: Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(response.isSuccessful && responseBody!= null ){
                        _searchList.value = ArrayList(responseBody.items)
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }catch (e: Exception){
            Log.d("Token e", e.toString())
        }
    }
    companion object{
        private const val TAG = "MainViewModel"
        private const val USERS = "Jihaan"
    }
}