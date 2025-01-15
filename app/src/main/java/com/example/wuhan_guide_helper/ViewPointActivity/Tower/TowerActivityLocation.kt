package com.example.wuhan_guide_helper.ViewPointActivity.Tower

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.time.format.TextStyle

class TowerActivityLocation : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerLocationScreen(
                    onSearchClick = {
                        // 跳转到 TowerActivity
                        val intent = Intent(this, TowerActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TowerLocationScreen(onSearchClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Travel Tips", // 标题文本
                        modifier = Modifier
                            .padding(start = 16.dp) // 靠左显示
                            .fillMaxWidth(), // 使文本占据整个宽度
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold// 文本左对齐
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
                            text = "Yellow Crane Tower Address",
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
                                    "Bring your passport as it may be required for ticket purchases or identity verification.\n" +
                                    "\n" +
                                    "Clothing:\n" +
                                    "The temperature in Wuhan varies depending on the season. In spring and autumn, it is mild but can get chilly in the morning and evening. Wear a light jacket, scarf, and comfortable walking shoes, as the area around the tower involves some walking.\n" +
                                    "\n" +
                                    "Rain Gear:\n" +
                                    "Wuhan can experience sudden rainfall, especially in spring. Carry a folding umbrella or disposable raincoat for convenience.\n" +
                                    "\n" +
                                    "Additional Tips:\n" +
                                    "\n" +
                                    "Bring water and sunscreen during summer, as it can get hot and sunny.\n" +
                                    "Follow the signs and staff instructions for safety and a smooth visit.\n" +
                                    "Be mindful of cultural etiquette, such as refraining from loud noises or littering.\n" +
                                    "Enjoy your visit to this iconic and historic landmark!",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TowerMap() {
    val yellowCraneTower = LatLng(30.544505702632577, 114.3023649295135)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(yellowCraneTower, 15f)
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
                state = MarkerState(position = yellowCraneTower),
                title = "Yellow Crane Tower"
            )
        }
    }
}