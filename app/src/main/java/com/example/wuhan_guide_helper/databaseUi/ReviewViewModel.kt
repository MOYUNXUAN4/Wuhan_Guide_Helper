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

    private val db = FirebaseFirestore.getInstance() // 初始化 Firestore

    // 获取所有评价
    val allReviews = repository.allReviews

    // 插入评价
    fun insert(location: String, description: String) = viewModelScope.launch {
        val userName = getCurrentUserName() // 获取当前登录用户的用户名
        Log.d("ReviewViewModel", "Inserting review with user name: $userName") // 打印用户名
        val review = Review(location = location, description = description, name = userName)
        repository.insert(review)
    }

    // 更新评价
    fun update(review: Review) = viewModelScope.launch {
        repository.update(review)
    }

    // 删除评价
    fun delete(review: Review) = viewModelScope.launch {
        repository.delete(review)
    }

    // 根据 id 查询单个评价
    fun getReviewById(id: Long) = repository.getReviewById(id)

    // 获取当前登录用户的用户名
    private suspend fun getCurrentUserName(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.d("ReviewViewModel", "Current user is null. User is not logged in.")
            return "匿名用户"
        }

        // 从 Firestore 获取用户名
        return try {
            val document = db.collection("users")
                .document(currentUser.uid)
                .get()
                .await() // 使用 await() 将异步操作转换为同步
            val userName = document.getString("username") ?: "匿名用户"
            Log.d("ReviewViewModel", "Fetched user name from Firestore: $userName")
            userName
        } catch (e: Exception) {
            Log.e("ReviewViewModel", "Failed to fetch user name from Firestore: ${e.message}")
            "匿名用户"
        }
    }
}