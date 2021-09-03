package com.example.week_nine_task.ui

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
import com.example.week_nine_task.model.PostResponseItem
import com.example.week_nine_task.viewmodel.MainViewModel

class CreatePost : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var title: AppCompatEditText
    lateinit var post: AppCompatEditText
    lateinit var submitPost: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        title = findViewById(R.id.title_post)
        post = findViewById(R.id.postTxt)
        submitPost = findViewById(R.id.submitPost)

        initCreatePostViewModel()
        submitPost.setOnClickListener {
            createPost()
        }
    }

    fun createPost(){
        if (title.text.toString().isEmpty() || post.text.toString().isEmpty()){
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }else{
            val post = PostResponseItem(1, 1, title.text.toString(), post.text.toString())
            viewModel.postPost(post)
        }

    }


    fun initCreatePostViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.postPostListLiveData.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                Log.d("CreatePost", "$it")
                viewModel.getPostListLiveData.value?.add(it)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("CreatePost", "Failed: $it")
            }
        })
    }
}