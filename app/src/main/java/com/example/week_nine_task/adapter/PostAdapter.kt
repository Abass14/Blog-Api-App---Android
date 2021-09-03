package com.example.week_nine_task.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_nine_task.R
import com.example.week_nine_task.model.PostResponseItem
import com.example.week_nine_task.ui.PostPage

class PostAdapter(val context: Context) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    var postList: ArrayList<PostResponseItem> = arrayListOf()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId = view.findViewById<TextView>(R.id.user_id)
        val title = view.findViewById<TextView>(R.id.title)
        val post = view.findViewById<TextView>(R.id.post)
        val postCardView = view.findViewById<CardView>(R.id.postCardView)

        fun bind(posts: PostResponseItem, context: Context){
            userId.text = posts.userId.toString()
            title.text = posts.title
            post.text = posts.body
            val id = posts.id
            postCardView.setOnClickListener {
                val intent = Intent(context, PostPage::class.java)
                intent.putExtra("id", id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(postList[position], context)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}