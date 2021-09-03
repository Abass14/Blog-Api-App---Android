package com.example.week_nine_task.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class CommentsResponse : ArrayList<CommentsResponseItem>()

@Parcelize
data class CommentsResponseItem(
    val postId: Int,
    val id: Int?,
    val name: String,
    val email: String,
    val body: String
) : Parcelable

data class Comment(
    val postId: Int? = null,
    val id: String? = null,
    val name: String? = null,
    val email: String,
    val body: String
)