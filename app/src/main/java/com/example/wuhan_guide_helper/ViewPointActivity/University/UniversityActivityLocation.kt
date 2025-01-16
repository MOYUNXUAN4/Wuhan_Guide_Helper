package com.example.wuhan_guide_helper.ViewPointActivity.University

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.wuhan_guide_helper.foodActivity.FoodActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class UniversityActivityLocation : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerLocationScreen(
                    onSearchClick = {
                        // 跳转到 UniversityActivity
                        val intent = Intent(this, UniversityActivity::class.java)
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
                IconButton(onClick = onSearchClick) { // 点击时跳转到 UniversityActivity
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
            TowerMap()

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
                            text = "Wuhan University Address",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Address: G9P7+CP8, Wuchang District, Wuhan, Hubei, China, 430072",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Entry Requirements:\n" +
                                    "Bring your passport for identity verification when entering the campus during peak seasons, especially during the cherry blossom festival. Some areas may require advance reservations or tickets.\n" +
                                    "Clothing:\n" +
                                    "The temperature in Wuhan varies by season. During the spring cherry blossom season, mornings and evenings can be cool, so a light jacket or sweater is recommended. Wear comfortable walking shoes, as the campus is expansive with many stairs and slopes.\n" +
                                    "Rain Gear:\n" +
                                    "Spring in Wuhan often brings unexpected rain showers. Carry a compact umbrella or a disposable raincoat to stay dry while exploring the outdoor areas of the campus.\n" +
                                    "Additional Tips:\n" +
                                    "Stay hydrated, especially during warm weather, and consider bringing a water bottle with you.\n" +
                                    "Respect campus rules by staying on designated paths and refraining from entering restricted areas.\n" +
                                    "Be mindful of noise levels, as the university is an academic institution where students are actively studying.\n" +
                                    "Keep the campus clean by properly disposing of litter in designated bins.\n" +
                                    "Take your time to enjoy the historic architecture, serene lake views, and seasonal flowers that make Wuhan University so unique.\n" +
                                    "Consider visiting early in the morning to avoid crowds, especially during cherry blossom season."
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TowerMap() {
    // 武汉大学的坐标
    val WudaUniversity = LatLng(30.536156993566298, 114.36542784591823)

    // 设置地图的初始位置和缩放级别
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(WudaUniversity, 16f) // 调整缩放级别
    }

    // 地图容器
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)) // 设置圆角
    ) {
        // Google 地图
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapType = MapType.NORMAL // 地图类型（普通、卫星等）
            )
        ) {
            // 手动添加武汉大学的标记
            Marker(
                state = MarkerState(position = WudaUniversity), // 标记的位置
                title = "Wuhan University", // 标记的标题
                snippet = "A famous university in Wuhan, China", // 标记的副标题
                onClick = {
                    Log.d("GoogleMap", "Marker clicked") // 点击标记时的日志
                    true
                }
            )
        }
    }
}