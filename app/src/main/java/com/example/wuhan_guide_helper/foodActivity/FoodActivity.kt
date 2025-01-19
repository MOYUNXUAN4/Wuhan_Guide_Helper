package com.example.wuhan_guide_helper.foodActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
            FoodInWuhanScreen(context = this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInWuhanScreen(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Food In Wuhan",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB497BD)
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
        ) {
            item {
                FoodCard(
                    imageRes = R.drawable.hot_dry_noodles,
                    title = "Wuhan Hot Dry Noodles",
                    description = stringResource(R.string.HotDryNoodles),
                    onClick = {
                        val intent = Intent(context, FoodLocation::class.java)
                        intent.putExtra("foodName", "Wuhan Hot Dry Noodles")
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FoodCard(
                    imageRes = R.drawable.doupi,
                    title = "Doupi",
                    description = stringResource(R.string.doupi),
                    onClick = {
                        val intent = Intent(context, FoodLocation::class.java)
                        intent.putExtra("foodName", "Doupi")
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                FoodCard(
                    imageRes = R.drawable.mianwo,
                    title = "Mianwo",
                    description = stringResource(R.string.mianwoIntroduction),
                    onClick = {
                        val intent = Intent(context, FoodLocation::class.java)
                        intent.putExtra("foodName", "Mianwo")
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FoodCard(
    imageRes: Int,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                    .clip(RoundedCornerShape(8.dp))
            )
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