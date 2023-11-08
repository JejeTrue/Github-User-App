package com.jejetrue.githubjj.data.retrofit
import com.jejetrue.githubjj.*
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{

        private const val KEY = "token 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"
        private const val BASE_URL = "https://api.github.com/"

        fun getApiService(): ApiService{

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor {
                val req = it.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", KEY)
                    .build()
                it.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}