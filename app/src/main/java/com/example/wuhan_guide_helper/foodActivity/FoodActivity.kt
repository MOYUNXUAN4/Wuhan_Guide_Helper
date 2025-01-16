package com.example.wuhan_guide_helper.foodActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.R

class FoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodInWuhanScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInWuhanScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Food In Wuhan",
                        color = Color.White, // 标题文字颜色为白色
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start // 标题左对齐
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB497BD) // TopAppBar 背景颜色保留为 #B497BD
                )
            )
        },
        containerColor = Color.White // Scaffold 背景颜色改为白色
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White) // Column 背景颜色改为白色
        ) {
            FoodCard(
                imageRes = R.drawable.hot_dry_noodles, // 替换为你的图片资源 ID
                title = "Wuhan Hot Dry Noodles",
                description = stringResource(R.string.HotDryNoodles)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FoodCard(imageRes: Int, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp), // Card 圆角
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // 添加阴影以营造悬浮感
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Card 背景颜色改为白色
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)) // 图片圆角
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black, // 标题文字颜色改为黑色
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.Black // 描述文字颜色改为黑色
            )
        }
    }
}