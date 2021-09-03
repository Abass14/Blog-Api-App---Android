package com.example.week_nine_task.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.week_nine_task.R
import com.example.week_nine_task.adapter.PostAdapter
import com.example.week_nine_task.model.PostResponseItem
import com.example.week_nine_task.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var postAdapter: PostAdapter
    lateinit var progressBar: ProgressBar
    lateinit var errorTxt: TextView
    lateinit var floatBtn: ImageButton
    lateinit var searchTxt: AppCompatEditText
    lateinit var searchBtn: ImageButton
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar_main)
        errorTxt = findViewById(R.id.errorTxt_main)
        floatBtn = findViewById(R.id.floatBtn)
        searchBtn = findViewById(R.id.searchBtn)
        searchTxt = findViewById(R.id.searchTxt)
        swipeRefreshLayout = findViewById(R.id.swipeLayout)

        floatBtn.setOnClickListener {
            navigateToCreatePost()
        }

        postAdapter = PostAdapter(this)
        bindUI()
        initViewModel()

        searchBtn.setOnClickListener {
            search(searchTxt.text.toString())
            Log.d("MainActivity", "clicked")
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            initViewModel()

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun search(searchTxt: String) {
        postAdapter.postList =  postAdapter.postList.filter {
            it.title.contains(searchTxt, ignoreCase = false)
        } as ArrayList<PostResponseItem>

        postAdapter.notifyDataSetChanged()

    }

    fun navigateToCreatePost(){
        val intent = Intent(this, CreatePost::class.java)
        startActivity(intent)
    }

    fun bindUI(){
        recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initViewModel(): MainViewModel{
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getPostListLiveData.observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it != null){
                postAdapter.postList = it
                postAdapter.notifyDataSetChanged()
            }else{
                errorTxt.visibility = View.VISIBLE
            }
        })
        viewModel.getPost()
        return viewModel
    }

}