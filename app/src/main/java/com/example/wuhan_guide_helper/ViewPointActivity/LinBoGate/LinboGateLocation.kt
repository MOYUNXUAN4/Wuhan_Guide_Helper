package com.example.wuhan_guide_helper.ViewPointActivity.LinBoGate

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.ViewPointActivity.Tower.TowerActivity
import com.example.wuhan_guide_helper.ViewPointActivity.Tower.TowerMap
import com.example.wuhan_guide_helper.foodActivity.FoodActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class LinboGateLocation : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerLocationScreen(
                    onSearchClick = {
                        val intent = Intent(this, LinboGateActivity::class.java)
                        startActivity(intent)
                    },
                    context = this // 传递 context
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TowerLocationScreen(onSearchClick: () -> Unit, context: android.content.Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Location&Tips", // 标题文本
                        modifier = Modifier
                            .padding(start = 16.dp) // 靠左显示
                            .fillMaxWidth(), // 使文本占据整个宽度
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold // 文本左对齐
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
                IconButton(onClick = onSearchClick) {
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
                IconButton(onClick = { /* TODO: Show on Map */ }) {
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
            // 地图
            LinBoGateMap()

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
                            text = "Linbo Gate Address",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Address: China, Hubei, Wuhan, Wuchang District, 299, East 70 meters,430074",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Entry Requirements:\n" +
                                    "Bring your passport for identity verification or ticket purchases, especially during peak seasons. Some areas around Lingbo Gate may have restricted access, so check in advance.\n" +
                                    "Clothing:\n" +
                                    "The temperature near East Lake varies with the season. In spring and autumn, mornings and evenings can be cool, so bring a light jacket or sweater. Comfortable walking shoes are a must, as you may stroll along the lake's pathways or climb gentle slopes for better views.\n" +
                                    "Rain Gear:\n" +
                                    "Wuhan is known for sudden rain showers, especially in spring and summer. Carry a compact umbrella or disposable raincoat to ensure an uninterrupted visit.\n" +
                                    "Additional Tips:\n" +
                                    "During summer, bring water, sunscreen, and a hat, as the lakeside can get hot and sunny.\n" +
                                    "Visit early in the morning to catch the breathtaking sunrise over East Lake, a truly poetic experience.\n" +
                                    "Respect the natural surroundings by staying on designated paths and avoiding littering.\n" +
                                    "Be mindful of cultural etiquette, keeping noise levels low to preserve the tranquility of the area.\n" +
                                    "If boating is available, ensure you follow safety guidelines and enjoy the serene beauty of the lake from the water.\n" +
                                    "Lingbo Gate is not just a destination but an experience—a place where nature and history merge harmoniously. Take your time to savor its charm and create lasting memories.",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LinBoGateMap() {
    val LinBoGate = LatLng(30.543754180384337, 114.37130701526223)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LinBoGate, 16f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)) // 设置圆角
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LinBoGate),
                title = "LinBo Gate"
            )
        }
    }
}