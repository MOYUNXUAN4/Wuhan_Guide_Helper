package com.example.wuhan_guide_helper.database

import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val reviewDao: ReviewDao) {

    val allReviews: Flow<List<Review>> = reviewDao.getAllReviews()

    suspend fun insert(review: Review) {
        reviewDao.insert(review)
    }

    suspend fun update(review: Review) {
        reviewDao.update(review)
    }

    suspend fun delete(review: Review) {
        reviewDao.delete(review)
    }

    fun getReviewById(id: Long): Flow<Review> {
        return reviewDao.getReviewById(id)
    }
}