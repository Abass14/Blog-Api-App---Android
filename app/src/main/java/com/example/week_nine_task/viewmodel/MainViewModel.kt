package com.example.week_nine_task.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week_nine_task.model.CommentsResponse
import com.example.week_nine_task.model.CommentsResponseItem
import com.example.week_nine_task.model.PostResponse
import com.example.week_nine_task.model.PostResponseItem
import com.example.week_nine_task.network.RetroService
import com.example.week_nine_task.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val MY_PREF =  "my pref"
    var myPref: SharedPreferences? = null

    private val _getPostListLiveData: MutableLiveData<PostResponse?> = MutableLiveData()
    val getPostListLiveData: LiveData<PostResponse?> = _getPostListLiveData

    private val _postPostListLiveData: MutableLiveData<PostResponseItem?> = MutableLiveData()
    val postPostListLiveData: LiveData<PostResponseItem?> = _postPostListLiveData

    private val _singlePostLiveData: MutableLiveData<PostResponseItem?> = MutableLiveData()
    val singlePostLiveData: LiveData<PostResponseItem?> = _singlePostLiveData

    private val _getCommentLiveData: MutableLiveData<CommentsResponse?> = MutableLiveData()
    val getCommentLiveData: LiveData<CommentsResponse?> = _getCommentLiveData

    private val _postCommentLiveData: MutableLiveData<CommentsResponseItem?> = MutableLiveData()
    val postCommentLiveData: LiveData<CommentsResponseItem?> = _postCommentLiveData

    val retrofitInstance = RetrofitInstance.retrofitInstance().create(RetroService::class.java)

    fun getPost(){
        val call = retrofitInstance.getPost()

        call.enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful){
                    _getPostListLiveData.postValue(response.body())
                }else{
                    _getPostListLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                _getPostListLiveData.postValue(null)
            }

        })
    }

    fun postPost(post: PostResponseItem){
        val call = retrofitInstance.createPost(post)
        call.enqueue(object : Callback<PostResponseItem>{
            override fun onResponse(call: Call<PostResponseItem>, response: Response<PostResponseItem>) {
                if (response.isSuccessful){
                    _postPostListLiveData.postValue(response.body())
                    Log.d("CreateViewModel", "${response.body()}")
                }else{
                    _postPostListLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PostResponseItem>, t: Throwable) {
                _postPostListLiveData.postValue(null)
                Log.d("CreateViewModel", "Failed")
            }

        })
    }

    fun getSinglePost(id: Int){
        val call = retrofitInstance.getSinglePost(id)
        call.enqueue(object : Callback<PostResponseItem>{
            override fun onResponse(call: Call<PostResponseItem>, response: Response<PostResponseItem>) {
                if (response.isSuccessful){
                    _singlePostLiveData.postValue(response.body())
                }else{
                    _singlePostLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PostResponseItem>, t: Throwable) {
                _singlePostLiveData.postValue(null)
            }

        })

    }


    fun getComment(id: Int){
        val call = retrofitInstance.getComments(id)
        call.enqueue(object : Callback<CommentsResponse>{
            override fun onResponse(
                call: Call<CommentsResponse>,
                response: Response<CommentsResponse>
            ) {
                if (response.isSuccessful){
                    _getCommentLiveData.postValue(response.body())
                }else{
                    _getCommentLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<CommentsResponse>, t: Throwable) {
                _getCommentLiveData.postValue(null)
            }

        })
    }

    val fakeResponseItem: MutableList<CommentsResponseItem> = mutableListOf()
    fun postComment(id: Int, comment: CommentsResponseItem){
        val call = retrofitInstance.postComments(comment, id)

        call.enqueue(object : Callback<CommentsResponseItem>{
            override fun onResponse(call: Call<CommentsResponseItem>, response: Response<CommentsResponseItem>) {

                if (response.isSuccessful){
                    _postCommentLiveData.postValue(response.body())
                    Log.d("MainViewModel", "Created: ${response.body()}")
                }else{
                    _postCommentLiveData.postValue(null)
                    Log.d("MainViewModel", "check: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<CommentsResponseItem>, t: Throwable) {


                _postCommentLiveData.postValue(null)
                Log.d("MainViewModel", "failed $comment")
                fakeResponseItem.add(comment)
            }

        })
    }
}