package com.example.wuhan_guide_helper

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.user.UserSignIn
import com.example.wuhan_guide_helper.databaseUi.ContextActivity
import com.example.wuhan_guide_helper.internet.TransferActivity
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.example.wuhan_guide_helper.user.UserDetailActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class MainScreenActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

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
                var username by remember { mutableStateOf("") } // 用户名状态

                // 获取当前用户的用户名
                LaunchedEffect(Unit) {
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        val userDoc = db.collection("users").document(userId).get().await()
                        username = userDoc.getString("username") ?: "User"
                    }
                }

                MainScreen(
                    attractions = attractions,
                    username = username, // 传递用户名
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
fun MainScreen(
    attractions: List<TouristAttraction>,
    username: String, // 接收用户名
    onSeeMoreClick: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            TopBar(username = username) // 传递用户名到 TopBar
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
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB497BD)
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
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
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun TopBar(username: String) {
    val context = LocalContext.current
    val auth = Firebase.auth

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = username, // 显示用户名
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
        IconButton(
            onClick = {
                Log.d("TopBar", "User icon clicked")
                try {
                    val user = auth.currentUser
                    if (user != null) {
                        // 用户已登录，跳转到 UserDetail 界面
                        val intent = Intent(context, UserDetailActivity::class.java)
                        intent.putExtra("email", user.email) // 传递邮箱
                        intent.putExtra("username", username) // 传递用户名
                        context.startActivity(intent)
                    } else {
                        // 用户未登录，跳转到登录界面
                        val intent = Intent(context, UserSignIn::class.java)
                        context.startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.e("TopBar", "Failed to start activity", e)
                }
            },
            modifier = Modifier.background(Color.Transparent)
        ) {
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
    val images = listOf(
        R.drawable.dq5,
        R.drawable.dq4,
        R.drawable.hh2,
        R.drawable.lb6,
        R.drawable.lb1,
        R.drawable.sa4
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
                "Viewpoint" -> onSeeMoreClick()
                "Emergency" -> {
                    val intent = Intent(context, ContextActivity::class.java)
                    context.startActivity(intent)
                }
                "Translate" -> {
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