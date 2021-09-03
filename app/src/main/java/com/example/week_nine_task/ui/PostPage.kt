package com.example.week_nine_task.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week_nine_task.R
import com.example.week_nine_task.adapter.CommentAdapter
import com.example.week_nine_task.model.CommentsResponseItem
import com.example.week_nine_task.model.PostResponseItem
import com.example.week_nine_task.utils.Firebase
import com.example.week_nine_task.viewmodel.MainViewModel

class PostPage : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var userId: TextView
    lateinit var title: TextView
    lateinit var post: TextView
    lateinit var back: ImageButton
    lateinit var commentAdapter: CommentAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var commentBtn: ImageButton
    var newComment: CommentsResponseItem? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_page)
        userId = findViewById(R.id.user_id_single)
        title = findViewById(R.id.title_single)
        post = findViewById(R.id.post_single)
        back = findViewById(R.id.backBtn)
        commentBtn = findViewById(R.id.commentBtn)
        commentAdapter = CommentAdapter()
        recyclerView = findViewById(R.id.commentRecyclerView)

        back.setOnClickListener {
            onBackPressed()
        }
        val id = intent.getIntExtra("id", 1)
        newComment = intent.getParcelableExtra<CommentsResponseItem>("it")
        Toast.makeText(this, newComment.toString(), Toast.LENGTH_LONG).show()

        commentBtn.setOnClickListener {
            navigationToPostComment(id)
        }

        bindCommentUI()
        initGetPostViewModel(id)
        initGetCommentViewModel(id)
    }


    fun navigationToPostComment(id: Int){
        val intent = Intent(this, PostComment::class.java)
        intent.putExtra("postId", id)
        startActivity(intent)
    }

    fun bindPostUI(posts: PostResponseItem){
        userId.text = posts.userId.toString()
        title.text = posts.title
        post.text = posts.body
    }

    fun bindCommentUI(){
        recyclerView.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(this@PostPage)
        }
    }

    fun initGetPostViewModel(id: Int) : MainViewModel{
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.singlePostLiveData.observe(this, Observer {
            if (it != null){
                bindPostUI(it)
            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getSinglePost(id)
        return viewModel
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initGetCommentViewModel(id: Int) : MainViewModel{
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getCommentLiveData.observe(this, Observer {
            if (it != null){
                commentAdapter.commentList = it.toMutableList()
                if (newComment != null){
                    commentAdapter.commentList.add(newComment!!)
                }
                commentAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "Error fetching comments", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getComment(id)
        return viewModel
    }
}