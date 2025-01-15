package com.example.wuhan_guide_helper.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseAuth

@Entity(tableName = "reviews") // 定义表名为 "reviews"
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 主键，自动生成
    val location: String, // 地点
    val description: String, // 描述
    val name: String // 用户名，默认从 Firebase 获取
) {
    companion object {
        // 获取当前登录用户的用户名
        private fun getCurrentUserName(): String {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return currentUser?.displayName ?: "Unknown User" // 如果用户未设置用户名，显示默认值
        }
    }
}