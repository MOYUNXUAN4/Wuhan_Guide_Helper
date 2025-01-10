package com.example.wuhan_guide_helper.databaseUi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.example.wuhan_guide_helper.MyApplication
import com.example.wuhan_guide_helper.database.Review
import com.example.wuhan_guide_helper.database.ReviewRepository
import com.example.wuhan_guide_helper.database.AppDatabase
import com.example.wuhan_guide_helper.databaseUi.ReviewViewModel
import com.example.wuhan_guide_helper.databaseUi.ReviewViewModelFactory

class ContextActivity : ComponentActivity() {

    // 初始化 ViewModel
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReviewScreen(reviewViewModel = reviewViewModel)
        }
    }
}

@Composable
fun ReviewScreen(reviewViewModel: ReviewViewModel) {
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    // 观察所有评价
    val allReviews by reviewViewModel.allReviews.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 输入表单
        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 添加评价按钮
        Button(onClick = {
            val review = Review(location = location, description = description, name = name)
            reviewViewModel.insert(review)
        }) {
            Text("Add Review")
        }

        // 显示评价列表
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allReviews) { review ->
                ReviewItem(review = review, onDelete = {
                    reviewViewModel.delete(review)
                })
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, onDelete: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Location: ${review.location}")
        Text(text = "Description: ${review.description}")
        Text(text = "Name: ${review.name}")
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}