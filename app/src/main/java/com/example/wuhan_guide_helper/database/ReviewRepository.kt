package com.example.wuhan_guide_helper.database

import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val reviewDao: ReviewDao) {

    // 获取所有评价
    val allReviews: Flow<List<Review>> = reviewDao.getAllReviews()

    // 插入评价
    suspend fun insert(review: Review) {
        reviewDao.insert(review)
    }

    // 更新评价
    suspend fun update(review: Review) {
        reviewDao.update(review)
    }

    // 删除评价
    suspend fun delete(review: Review) {
        reviewDao.delete(review)
    }

    // 根据 id 查询单个评价
    fun getReviewById(id: Long): Flow<Review> {
        return reviewDao.getReviewById(id)
    }
}