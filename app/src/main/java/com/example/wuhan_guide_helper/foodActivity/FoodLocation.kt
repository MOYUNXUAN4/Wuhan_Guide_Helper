package com.example.wuhan_guide_helper.foodActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class FoodLocation : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerLocationScreen()
            }
        }
    }
}

@Composable
fun MapCard(
    title: String,
    description: String,
    location: LatLng,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(location, 15f)
                }
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        mapType = MapType.NORMAL
                    )
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = title,
                        snippet = description
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TowerLocationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Location",
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
        ) {
            item {
                MapCard(
                    title = "Yujia Restaurant",
                    description = "32 Tianjin Rd, Jiang'An, Wuhan, Hubei, China, 430021",
                    location = LatLng(30.585113598750024, 114.29734954228803)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                MapCard(
                    title = "Yuanwei Restaurant",
                    description = "628 Wuluo Rd, Wuchang District, Wuhan, Hubei, China, 430074",
                    location = LatLng(30.529299660384332, 114.34332726431865)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                MapCard(
                    title = "Wojia Restaurant",
                    description = "H7PP+RFH, Jiefang Blvd,  Jiang'An, Wuhan, Hubei, China, 430000",
                    location = LatLng(30.587512326614817, 114.28636774556841)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}