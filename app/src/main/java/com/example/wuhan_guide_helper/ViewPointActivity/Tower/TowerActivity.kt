package com.example.wuhan_guide_helper.ViewPointActivity.Tower

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

class TowerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerScreen(
                    onShowOnMapClick = {
                        // 跳转到 TowerActivityLocation
                        val intent = Intent(this, TowerActivityLocation::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun TowerScreen(onShowOnMapClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Yellow Crane Tower Introduction", // 标题文本
                        modifier = Modifier.padding(start = 16.dp) // 靠左显示
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB497BD) // 背景颜色
                )
            )
        },
        bottomBar = {
            // 底部按钮行
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = { /* TODO: Navigate to Restaurant */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_restaurant_btn),
                        contentDescription = null,
                        tint = Color(0xFFB497BD) // 统一颜色
                    )
                }
                Divider(
                    color = Color(0xFFB497BD),
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                )
                IconButton(onClick = { /* TODO: Navigate to Hotel */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hotel_btn),
                        contentDescription = null,
                        tint = Color(0xFFB497BD) // 统一颜色
                    )
                }
                Divider(
                    color = Color(0xFFB497BD),
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                )
                IconButton(onClick = { /* TODO: Search */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = null,
                        tint = Color(0xFFB497BD) // 统一颜色
                    )
                }
                Divider(
                    color = Color(0xFFB497BD),
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                )
                IconButton(onClick = onShowOnMapClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.show_on_map),
                        contentDescription = null,
                        tint = Color(0xFFB497BD) // 统一颜色
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 轮播图
            HeaderImage()

            Spacer(modifier = Modifier.height(16.dp))

            // 卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    item {
                        Text(
                            text = "Yellow Crane Tower Introduction",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.YellowCraneTowerIntroduction),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Website: https://www.tripadvisor.com.my/Attraction_Review-g297437-d504962-Reviews-Yellow_Crane_Tower-Wuhan_Hubei.html",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderImage() {
    val images = listOf(
        R.drawable.hh2,
        R.drawable.hh3,
        R.drawable.hh4,
        R.drawable.hh6,
        R.drawable.hh8,
        R.drawable.hh9,
        R.drawable.hh10
    )

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
                contentDescription = "Header Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(32.dp))
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