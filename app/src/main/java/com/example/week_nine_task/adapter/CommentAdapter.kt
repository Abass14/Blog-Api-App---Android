package com.example.week_nine_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_nine_task.R
import com.example.week_nine_task.model.CommentsResponse
import com.example.week_nine_task.model.CommentsResponseItem

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    var commentList: MutableList<CommentsResponseItem> = mutableListOf()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.userName)
        val email = view.findViewById<TextView>(R.id.emailComment)
        val comments = view.findViewById<TextView>(R.id.postComment)

        fun bind(comment: CommentsResponseItem){
            name.text = comment.name
            email.text = comment.email
            comments.text = comment.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}