package com.example.wuhan_guide_helper.ViewPointActivity.University

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
import androidx.compose.ui.text.style.TextAlign
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.foodActivity.FoodActivity
import com.example.wuhan_guide_helper.hotel.HotelListActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

class UniversityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerScreen(
                    onShowOnMapClick = {
                        // 跳转到 UniversityActivityLocation
                        val intent = Intent(this, UniversityActivityLocation::class.java)
                        startActivity(intent)
                    },
                    context = this // 传递 context
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun TowerScreen(onShowOnMapClick: () -> Unit, context: android.content.Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "WuHan University Introduction",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB497BD)
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
                IconButton(onClick = {
                    // 跳转到 FoodActivity
                    val intent = Intent(context, FoodActivity::class.java)
                    context.startActivity(intent)
                }) {
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
                IconButton(onClick = {
                    // 跳转到 HotelListActivity
                    val intent = Intent(context, HotelListActivity::class.java)
                    context.startActivity(intent)
                }) {
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
                            text = "WuHan University Introduction",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.WuDaIntroduction),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Website: https://en.whu.edu.cn/",
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
        R.drawable.sa1,
        R.drawable.sa2,
        R.drawable.sa3,
        R.drawable.sa4,
        R.drawable.sa5,
        R.drawable.sa6,
        R.drawable.sa7
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
                .heightIn(min = 240.dp) // 设置最小高度
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f) // 设置宽高比为 16:9
                    .clip(RoundedCornerShape(32.dp))
            ) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Header Image $page",
                    contentScale = ContentScale.Crop, // 使用 Crop 确保图片填充容器
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center // 调整裁切的对齐方式
                )
            }
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