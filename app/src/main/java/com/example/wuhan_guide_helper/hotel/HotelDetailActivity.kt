package com.example.wuhan_guide_helper.hotel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import androidx.compose.material3.MaterialTheme
import com.example.wuhan_guide_helper.R

class HotelDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hotelName = intent.getStringExtra("HOTEL_NAME") ?: "Unknown Hotel"
        val (images, description) = getHotelDetails(hotelName)

        setContent {
            HotelDetailScreen(hotelName, images, description, context = this)
        }
    }

    private fun getHotelDetails(hotelName: String): Pair<List<Int>, String> {
        return when (hotelName) {
            "KaiYue" -> {
                val images = listOf(R.drawable.hotel1, R.drawable.hotel2, R.drawable.hotel3, R.drawable.hotel5)
                val description = getString(R.string.hotelIntroduction)
                images to description
            }
            "Hilton" -> {
                val images = listOf(R.drawable.hotel5, R.drawable.hotel6, R.drawable.hotel7, R.drawable.hotel8)
                val description = getString(R.string.hotelIntroduction)
                images to description
            }
            "WanHao" -> {
                val images = listOf(R.drawable.hotel9, R.drawable.hotel10, R.drawable.hotel11)
                val description = getString(R.string.hotelIntroduction)
                images to description
            }
            else -> throw IllegalArgumentException("Unknown hotel name: $hotelName")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailScreen(
    hotelName: String,
    images: List<Int>,
    description: String,
    context: Context
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hotel Introduction",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB497BD) // TopBar 背景颜色保持不变
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // 背景改为白色
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                HeaderImage(images) // 酒店图片轮播
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White), // 卡片背景改为白色
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // 添加阴影
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        val intent = Intent(context, HotelLocationScreen::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD))
                ) {
                    Text("View Location", color = Color.White)
                }
            }
        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderImage(images: List<Int>) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Hotel Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            activeColor = Color(0xFFB497BD),
            inactiveColor = Color(0xFFB497BD).copy(alpha = 0.4f)
        )
    }
}