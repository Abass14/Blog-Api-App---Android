package com.example.week_nine_task.network

import com.example.week_nine_task.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    //https://jsonplaceholder.typicode.com/
    //https://jsonplaceholder.typicode.com/posts
    //https://jsonplaceholder.typicode.com/posts/1
    //https://jsonplaceholder.typicode.com/posts/1/comments
    //https://jsonplaceholder.typicode.com/posts/1/comments?postId=1

    companion object{

        fun retrofitInstance() : Retrofit{

            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging )

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}