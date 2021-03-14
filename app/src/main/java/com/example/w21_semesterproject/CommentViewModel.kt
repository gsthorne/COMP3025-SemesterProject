package com.example.w21_semesterproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CommentViewModel (restaurantId : String) : ViewModel() {
    private val comments : MutableLiveData<List<Comment>> by lazy {
        MutableLiveData()
    }

    init {
        // query the db (firestore) to get a list of comments
        val db = FirebaseFirestore.getInstance().collection("comments")
                .whereEqualTo("restaurantId", restaurantId)

        db.addSnapshotListener { documents, exception ->
            if (exception != null) {
                Log.w("DBQUERY", "Listen failed")
                return@addSnapshotListener
            }
            documents?.let {
                val commentList = ArrayList<Comment>()
                for (document in documents) {
                    val comment = document.toObject(Comment::class.java)
                    commentList.add(comment)
                }
                comments.value = commentList
            }
        }
    }

    fun getComments() : LiveData<List<Comment>> {
        return comments
    }
}