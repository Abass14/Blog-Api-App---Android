package com.example.week_nine_task.network

import com.example.week_nine_task.model.CommentsResponse
import com.example.week_nine_task.model.CommentsResponseItem
import com.example.week_nine_task.model.PostResponse
import com.example.week_nine_task.model.PostResponseItem
import retrofit2.Call
import retrofit2.http.*

interface RetroService {

    @GET("posts")
    fun getPost() : Call<PostResponse>

    @POST("posts")
    fun createPost(@Body params: PostResponseItem) : Call<PostResponseItem>

    @GET("posts/{post_id}")
    fun getSinglePost(@Path("post_id") postId: Int) : Call<PostResponseItem>

    @GET("posts/{post_id}/comments")
    fun getComments(@Path("post_id") postId: Int) : Call<CommentsResponse>

    @POST("posts/{post_id}/comments")
    fun postComments(@Body params: CommentsResponseItem, @Path("post_id") postId: Int) : Call<CommentsResponseItem>
}