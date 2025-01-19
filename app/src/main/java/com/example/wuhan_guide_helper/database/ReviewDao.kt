package com.example.wuhan_guide_helper.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    @Update
    suspend fun update(review: Review)

    @Delete
    suspend fun delete(review: Review)

    @Query("SELECT * FROM reviews WHERE id = :id")
    fun getReviewById(id: Long): Flow<Review>

    @Query("SELECT * FROM reviews ORDER BY name ASC")
    fun getAllReviews(): Flow<List<Review>>
}