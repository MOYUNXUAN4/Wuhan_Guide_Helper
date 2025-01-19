package com.example.wuhan_guide_helper.databaseUi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wuhan_guide_helper.database.Review
import com.example.wuhan_guide_helper.database.ReviewRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()


    val allReviews = repository.allReviews


    fun insert(location: String, description: String) = viewModelScope.launch {
        val userName = getCurrentUserName() // 获取当前登录用户的用户名
        Log.d("ReviewViewModel", "Inserting review with user name: $userName") // 打印用户名
        val review = Review(location = location, description = description, name = userName)
        repository.insert(review)
    }


    fun update(review: Review) = viewModelScope.launch {
        repository.update(review)
    }


    fun delete(review: Review) = viewModelScope.launch {
        repository.delete(review)
    }


    fun getReviewById(id: Long) = repository.getReviewById(id)


    private suspend fun getCurrentUserName(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.d("ReviewViewModel", "Current user is null. User is not logged in.")
            return "Unknown User"
        }


        return try {
            val document = db.collection("users")
                .document(currentUser.uid)
                .get()
                .await()
            val userName = document.getString("username") ?: "Unknown User"
            Log.d("ReviewViewModel", "Fetched user name from Firestore: $userName")
            userName
        } catch (e: Exception) {
            Log.e("ReviewViewModel", "Failed to fetch user name from Firestore: ${e.message}")
            "Unknown User"
        }
    }
}