package com.jejetrue.githubjj.data.retrofit

import com.jejetrue.githubjj.data.response.DetailUserResponse
import com.jejetrue.githubjj.data.response.GithubResponse
import com.jejetrue.githubjj.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun search(
        @Query("q") username: String?
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<ArrayList<ItemsItem>>
}