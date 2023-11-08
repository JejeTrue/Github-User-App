package com.jejetrue.githubjj.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jejetrue.githubjj.data.response.DetailUserResponse
import com.jejetrue.githubjj.data.response.ItemsItem
import com.jejetrue.githubjj.data.retrofit.ApiConfig
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing


    private val _followers = MutableLiveData<ArrayList<ItemsItem>>()
    val getFollwers: LiveData<ArrayList<ItemsItem>> =_followers

    private val _following = MutableLiveData<ArrayList<ItemsItem>>()
    val getFollowing: LiveData<ArrayList<ItemsItem>> = _following


    fun findUser(input: String){
        try {
            _isLoadingDetail.value = true
            val client = ApiConfig.getApiService().getUserDetails(input)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoadingDetail.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _detailUser.value = response.body()
                    }
                }
                override fun onFailure(call: retrofit2.Call<DetailUserResponse>, t: Throwable) {
                    _isLoadingDetail.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }  catch (e: Exception){
            Log.d("Token e", e.toString())
        }

    }

    fun getFollower(input: String?){
        try {
            _isLoadingFollower.value = true

            val client = ApiConfig.getApiService().getFollowers(input)
            client.enqueue(object: Callback<ArrayList<ItemsItem>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    _isLoadingFollower.value = false
                    val responseBody = response.body()
                    if(response.isSuccessful && responseBody!=null){
                        _followers.value = ArrayList(responseBody)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<ItemsItem>>, t: Throwable) {
                    _isLoadingFollower.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")

                }

            })
        }catch (e: Exception){
            Log.d("Token e", e.toString())
        }

    }

    fun getFollowing(input: String?){
        try {
            _isLoadingFollowing.value = true
            val client = ApiConfig.getApiService().getFollowing(input)
            client.enqueue(object: Callback<ArrayList<ItemsItem>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    _isLoadingFollowing.value =false

                    val responseBody = response.body()
                    if(response.isSuccessful && responseBody != null){
                        _following.value = ArrayList(responseBody)
                    }

                }

                override fun onFailure(call: retrofit2.Call<ArrayList<ItemsItem>>, t: Throwable) {
                    _isLoadingFollowing.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")

                }
            })

        }catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
    companion object{
        private const val TAG = "DetailViewModel"
    }


}