package com.example.wuhan_guide_helper

import android.app.Application
import com.example.wuhan_guide_helper.database.AppDatabase
import com.example.wuhan_guide_helper.database.ReviewRepository

class MyApplication : Application() {

    // 通过 lazy 延迟初始化数据库和 Repository
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ReviewRepository(database.reviewDao()) }
}