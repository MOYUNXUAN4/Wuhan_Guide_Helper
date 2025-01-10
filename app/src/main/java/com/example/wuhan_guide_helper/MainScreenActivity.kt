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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme

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

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.dq5),
        contentDescription = "Header Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(32.dp))
    )
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