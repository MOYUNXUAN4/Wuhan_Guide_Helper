package com.example.wuhan_guide_helper.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseAuth

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val location: String,
    val description: String,
    val name: String
) {
    companion object {
        private fun getCurrentUserName(): String {
            val currentUser = FirebaseAuth.getInstance().currentUser
            return currentUser?.displayName ?: "Unknown User"
        }
    }
}