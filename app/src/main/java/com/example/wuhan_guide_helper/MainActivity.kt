package com.example.wuhan_guide_helper

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 定义 attractions 列表
        val attractions = listOf(
            TouristAttraction(
                "Yellow Crane Tower",
                "The Yellow Crane Tower rises over Wuhan, a timeless sentinel, watching the Yangtze flow beneath golden eaves.",
                R.drawable.hh4
            ),
            TouristAttraction(
                "Wuhan University Sakura",
                "At Wuhan University, cherry blossoms bloom like delicate pink clouds, weaving a fleeting dream of spring across the ancient campus.",
                R.drawable.sa6
            ),
            TouristAttraction(
                "Sunrise at Lingbo Gate",
                "At Lingbo Gate, the sunrise paints the sky with hues of gold and crimson, casting a tranquil glow over the Yangtze River, where waves gently dance to the morning's first light.",
                R.drawable.lb1
            )
        )

        setContent {
            Wuhan_Guide_HelperTheme {
                MainScreen(attractions = attractions) // 传递 attractions 列表
            }
        }
    }
}

@Composable
fun MainScreen(attractions: List<TouristAttraction>) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        TopBar()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // Header Image and Category Buttons as the first item in LazyColumn
            item {
                Column {
                    HeaderImage()
                    CategoryButtons()
                }
            }
            // Tourist Attraction List items
            items(attractions) { attraction ->
                AttractionItem(attraction)
            }
            // See More Button
            item {
                Button(
                    onClick = { /* 查看更多逻辑 */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "See More", fontSize = 18.sp)
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
fun CategoryButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)  // 设置两行之间的间隔
    ) {
        val row1Categories = listOf("Food", "Viewpoint", "Hotel")
        val row2Categories = listOf("Translate", "Emergency")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row1Categories.forEach { category ->
                CategoryButton(category = category)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            row2Categories.forEach { category ->
                CategoryButton(category = category)
            }
        }
    }
}

@Composable
fun CategoryButton(category: String) {
    val iconRes = when (category) {
        "Food" -> R.drawable.ic_restaurant_btn
        "Viewpoint" -> R.drawable.ic_viewpoint_btn
        "Hotel" -> R.drawable.ic_hotel_btn
        "Translate" -> R.drawable.ic_transalate_btn
        "Emergency" -> R.drawable.emergency
        else -> R.drawable.default_icon
    }

    Button(
        onClick = { /* 按钮逻辑 */ },
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

data class TouristAttraction(val name: String, val description: String, val imageRes: Int)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val attractions = listOf(
        TouristAttraction("Yellow Crane Tower", "The Yellow Crane Tower description", R.drawable.hh4),
        TouristAttraction("Wuhan University Sakura", "Cherry blossoms description", R.drawable.sa6),
        TouristAttraction("Sunrise at Lingbo Gate", "Sunrise description", R.drawable.lb1)
    )
    MainScreen(attractions = attractions)
}



