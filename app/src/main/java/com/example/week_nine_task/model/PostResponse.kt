package com.example.week_nine_task.model

class PostResponse : ArrayList<PostResponseItem>()

data class PostResponseItem(
    val userId: Int?,
    val id: Int?,
    val title: String,
    val body: String
)