package com.example.wuhan_guide_helper.database



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews") // 定义表名为 "reviews"
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 主键，自动生成
    val location: String, // 地点
    val description: String, // 描述
    val name: String // 用户名
)