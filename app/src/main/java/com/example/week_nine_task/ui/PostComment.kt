package com.example.week_nine_task.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.week_nine_task.R
import com.example.week_nine_task.adapter.CommentAdapter
import com.example.week_nine_task.model.CommentsResponseItem
import com.example.week_nine_task.utils.Firebase
import com.example.week_nine_task.viewmodel.MainViewModel

class PostComment : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var name: AppCompatEditText
    lateinit var email: AppCompatEditText
    lateinit var commentBtn: Button
    lateinit var comments: AppCompatEditText
    lateinit var commentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comment)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        commentBtn = findViewById(R.id.post)
        comments = findViewById(R.id.commentTxt)

        commentAdapter = CommentAdapter()

        val postId = intent.getIntExtra("postId", 1)
        initPostCommentViewModel()
        commentBtn.setOnClickListener {
            createComment(postId)
        }
    }

    fun createComment(id:Int){
        viewModel.postPostListLiveData
        val comment = CommentsResponseItem(
            id,
            1,
            name.text.toString(),
            email.text.toString(),
            comments.text.toString())
        if (name.text.toString().isEmpty()){
            Toast.makeText(this, "Name field can't be empty", Toast.LENGTH_SHORT).show()
        }else if (email.text.toString().isEmpty()){
            Toast.makeText(this, "Email field can't be empty", Toast.LENGTH_SHORT).show()
        }else if (comments.text.toString().isEmpty()){
            Toast.makeText(this, "Comment field can't be empty", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.postComment(id, comment)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun initPostCommentViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.postCommentLiveData.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                Log.d("PostComment", "Created: $it")
                val intent = Intent(this, PostPage::class.java)
                intent.putExtra("it", it)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("PostComment", "NOT WORKING $it")
            }
        })
    }
}