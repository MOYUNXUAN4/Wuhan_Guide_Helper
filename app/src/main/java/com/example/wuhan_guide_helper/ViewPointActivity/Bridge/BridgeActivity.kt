package com.example.wuhan_guide_helper.ViewPointActivity.Bridge


import android.content.Context
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
import com.example.wuhan_guide_helper.R
import com.example.wuhan_guide_helper.foodActivity.FoodActivity
import com.example.wuhan_guide_helper.hotel.HotelListActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

class BridgeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                TowerScreen(
                    onShowOnMapClick = {
                        val intent = Intent(this, BridgeLocation::class.java)
                        startActivity(intent)
                    },
                    context = this
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun TowerScreen(onShowOnMapClick: () -> Unit, context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wuhan Yangtze River Bridge Introduction",
                        modifier = Modifier.padding(start = 16.dp),
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
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, FoodActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_restaurant_btn),
                        contentDescription = null,
                        tint = Color(0xFFB497BD)
                    )
                }
                Divider(
                    color = Color(0xFFB497BD),
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                )
                IconButton(onClick = {
                    val intent = Intent(context, HotelListActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_hotel_btn),
                        contentDescription = null,
                        tint = Color(0xFFB497BD)
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
                            text = "Wuhan Yangtze River Bridge Introduction",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Stretching gracefully over the Yangtze River, the Wuhan Yangtze River Bridge is a marvel of history and engineering. Built in 1957 as the first bridge to unite the river’s northern and southern banks by road and rail, it became a symbol of progress and the dreams of a new era. Its steel framework rises proudly, carrying countless journeys across its span while holding echoes of the past.\n" +
                                    "\n" +
                                    "In the glow of a summer sunset, the bridge takes on an ethereal beauty. The river below shimmers in shades of amber and rose, reflecting the fading light like a secret waiting to be shared. The air hums with a quiet nostalgia, as if the scene belongs to a story of youthful summers—a fleeting moment of innocence and discovery. Cyclists ride leisurely, their shadows stretching long, while friends gather to watch the sun dip below the horizon. The bridge, in this golden hour, is not just a crossing—it’s a place where memories linger, where the river and the sky meet to whisper stories of life, love, and the boundless energy of youth.",
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
        R.drawable.dq1,
        R.drawable.dq2,
        R.drawable.dq3,
        R.drawable.dq4,
        R.drawable.dq5,
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