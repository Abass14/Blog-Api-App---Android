package com.example.week_nine_task.utils

import androidx.recyclerview.widget.RecyclerView
import com.example.week_nine_task.adapter.CommentAdapter
import com.example.week_nine_task.model.Comment
import com.example.week_nine_task.model.CommentsResponseItem
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

object Firebase {

    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Comments")

    fun add(comment: CommentsResponseItem) : Task<Void> {
        return databaseReference.child(comment.postId.toString()).setValue(comment)
    }


    fun retrieveContact(id:Int, commentList: MutableList<CommentsResponseItem>, mainRecView: RecyclerView) : Boolean {
        var result = true
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    commentList.clear()
                    for (userSnapshot in snapshot.children){
                        val comment = userSnapshot.getValue(CommentsResponseItem::class.java)
                        commentList.add(comment!!)
                    }
                    mainRecView.adapter = CommentAdapter()
                    result = true
                }else{
                    result = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return result
    }
}