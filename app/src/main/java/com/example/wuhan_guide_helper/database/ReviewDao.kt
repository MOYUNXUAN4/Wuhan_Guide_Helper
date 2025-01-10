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

    // 插入评价，如果冲突则忽略
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    // 更新评价
    @Update
    suspend fun update(review: Review)

    // 删除评价
    @Delete
    suspend fun delete(review: Review)

    // 根据 id 查询单个评价
    @Query("SELECT * FROM reviews WHERE id = :id")
    fun getReviewById(id: Long): Flow<Review>

    // 查询所有评价，按 name 升序排列
    @Query("SELECT * FROM reviews ORDER BY name ASC")
    fun getAllReviews(): Flow<List<Review>>
}