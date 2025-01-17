package com.example.wuhan_guide_helper.ViewPointActivity.Bridge

import android.content.Context
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
import com.example.wuhan_guide_helper.foodActivity.FoodActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class BridgeLocation : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerLocationScreen(
                    onSearchClick = {
                        val intent = Intent(this, BridgeActivity::class.java)
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
fun TowerLocationScreen(onSearchClick: () -> Unit, context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Location&Tips",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold ,
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
                IconButton(onClick = onSearchClick) { // 点击时跳转到 TowerActivity
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
            BridgeMap()

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
                            text = "Wuhan Yangtze River Bridge Address",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Address: G8V2+RW5, Huanghelou E Rd, Wuchang District, Wuhan, Hubei, China, 430060",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Entry Requirements:\n" +
                                    "No ticket is required to access the Wuhan Yangtze River Bridge walkway, but bring your passport for identity verification if needed, especially during peak travel seasons or for certain activities nearby.\n" +
                                    "Clothing:\n" +
                                    "The temperature in Wuhan varies with the season. During summer evenings, it’s warm but can get breezy by the river. A light jacket may be comfortable after sunset. Wear comfortable shoes for walking along the bridge or exploring nearby areas.\n" +
                                    "Rain Gear:\n" +
                                    "Wuhan is known for sudden rain showers, especially in spring and summer. Carry a folding umbrella or a disposable raincoat to stay prepared for unexpected weather changes.\n" +
                                    "Additional Tips:\n" +
                                    "Arrive early to secure a good spot for viewing the sunset and avoid crowds, particularly during weekends or holidays.\n" +
                                    "During summer, bring water, sunscreen, and a hat to protect yourself from the sun before it sets.\n" +
                                    "Respect the local environment and cultural etiquette. Avoid littering, and keep noise levels low to maintain the serene atmosphere of the sunset.\n" +
                                    "Use pedestrian paths and designated areas for safety, and avoid crossing into restricted zones on the bridge.\n" +
                                    "Bring a camera or smartphone to capture the stunning colors of the sunset reflecting on the Yangtze River.\n" +
                                    "Consider exploring nearby attractions like the Hubu Alley for local snacks or enjoying an evening boat ride on the river for a unique perspective of the bridge at twilight.\n" +
                                    "Watching the sunset from the Wuhan Yangtze River Bridge is a mesmerizing experience—a perfect moment to embrace the beauty of the river, the glow of the horizon, and the calm of the evening sky.",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BridgeMap() {
    val bridge = LatLng(30.550422736702437, 114.29455210638402)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bridge, 15f)
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
                state = MarkerState(position = bridge),
                title = "bridge"
            )
        }
    }
}