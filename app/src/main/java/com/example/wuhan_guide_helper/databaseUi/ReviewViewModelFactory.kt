package com.example.wuhan_guide_helper.databaseUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wuhan_guide_helper.database.ReviewRepository

class ReviewViewModelFactory(private val repository: ReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}