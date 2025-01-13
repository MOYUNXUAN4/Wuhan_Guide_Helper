package com.example.wuhan_guide_helper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.databaseUi.ContextActivity
import com.example.wuhan_guide_helper.internet.TransferActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

class MainScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val attractions = listOf(
            TouristAttraction(
                getString(R.string.yellow_crane_tower),
                getString(R.string.yellow_crane_tower_Description),
                R.drawable.hh4
            ),
            TouristAttraction(
                getString(R.string.wuhan_university_sakura),
                getString(R.string.wuhan_university_sakura_Description),
                R.drawable.sa6
            ),
            TouristAttraction(
                getString(R.string.sunrise_at_lingbo_gate),
                getString(R.string.sunRiseAtLingboGateDescription),
                R.drawable.lb1
            )
        )

        setContent {
            Wuhan_Guide_HelperTheme {
                MainScreen(
                    attractions = attractions,
                    onSeeMoreClick = {
                        val intent = Intent(this, ViewpointActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(attractions: List<TouristAttraction>, onSeeMoreClick: () -> Unit) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            TopBar()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Column {
                        HeaderImage()
                        CategoryButtons(onSeeMoreClick = onSeeMoreClick)
                    }
                }

                items(attractions) { attraction ->
                    AttractionItem(attraction)
                }
            }
        }

        // 悬浮按钮
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
        ) {
            Button(
                onClick = onSeeMoreClick,
                shape = RoundedCornerShape(32.dp), // 使用与 HeaderImage 相同的圆角形状
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB497BD) // 恢复为纯色背景
                ),
                modifier = Modifier
                    .width(120.dp) // 设置宽度
                    .height(48.dp), // 设置高度
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp, // 默认阴影
                    pressedElevation = 12.dp // 按下时的阴影
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                        contentDescription = "See More",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp) // 放大图标
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { /* 切换夜间模式 */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_night_mode),
                contentDescription = "Night Mode",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = { /* 用户配置逻辑 */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = "User",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderImage() {
    // 图片资源列表
    val images = listOf(
        R.drawable.dq5,
        R.drawable.dq4,
        R.drawable.hh2,
        R.drawable.lb6,
        R.drawable.lb1,
        R.drawable.sa4
    )

    // Pager 状态
    val pagerState = rememberPagerState()

    // 自动轮播逻辑
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // 3 秒切换一次
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {
        // 横向滑动图片
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

        // 添加指示器
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            activeColor = Color(0xFFB497BD), // 修改为指定的颜色
            inactiveColor = Color(0xFFB497BD).copy(alpha = 0.4f) // 使用相同颜色但降低透明度
        )
    }
}

@Composable
fun CategoryButtons(onSeeMoreClick: () -> Unit) {
    val row1Categories = listOf("Food", "Viewpoint", "Hotel")
    val row2Categories = listOf("Translate", "Emergency")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row1Categories.forEach { category ->
                CategoryButton(category = category, onSeeMoreClick = onSeeMoreClick)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row2Categories.forEach { category ->
                CategoryButton(category = category, onSeeMoreClick = onSeeMoreClick)
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, onSeeMoreClick: () -> Unit) {
    val context = LocalContext.current
    val iconRes = when (category) {
        "Food" -> R.drawable.ic_restaurant_btn
        "Viewpoint" -> R.drawable.ic_viewpoint_btn
        "Hotel" -> R.drawable.ic_hotel_btn
        "Translate" -> R.drawable.ic_transalate_btn
        "Emergency" -> R.drawable.emergency
        else -> R.drawable.default_icon
    }

    Button(
        onClick = {
            when (category) {
                "Viewpoint" -> onSeeMoreClick() // 调用传入的 onSeeMoreClick 处理 "Viewpoint" 按钮点击
                "Emergency" -> {
                    // 跳转到 ContextActivity
                    val intent = Intent(context, ContextActivity::class.java)
                    context.startActivity(intent)
                }
                "Translate" -> {
                    // 跳转到 TransferActivity
                    val intent = Intent(context, TransferActivity::class.java)
                    context.startActivity(intent)
                }
            }
        },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)),
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .height(42.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = category,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = category, fontSize = 12.sp)
    }
}

@Composable
fun AttractionItem(attraction: TouristAttraction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFFF1F1F1), RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = attraction.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(40.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = attraction.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = attraction.description,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

data class TouristAttraction(
    val name: String,
    val description: String,
    val imageRes: Int
)