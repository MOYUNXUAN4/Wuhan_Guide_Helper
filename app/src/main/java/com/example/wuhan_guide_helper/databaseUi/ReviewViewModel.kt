package com.example.wuhan_guide_helper.databaseUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wuhan_guide_helper.database.Review
import com.example.wuhan_guide_helper.database.ReviewRepository
import kotlinx.coroutines.launch

class ReviewViewModel(private val repository: ReviewRepository) : ViewModel() {

    // 获取所有评价
    val allReviews = repository.allReviews

    // 插入评价
    fun insert(review: Review) = viewModelScope.launch {
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
}