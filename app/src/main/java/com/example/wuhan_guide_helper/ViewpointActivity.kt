package com.example.wuhan_guide_helper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme

data class ViewPoint(
    val name: String,
    val description: String,
    val imageRes: Int
)

class ViewpointActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wuhan_Guide_HelperTheme {
                ViewPointScreen(viewPoints = generateViewPoints())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPointScreen(viewPoints: List<ViewPoint>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "View Point Introduction",
                        fontSize = 20.sp,
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
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewPoints) { viewPoint ->
                ViewPointCard(viewPoint)
            }
        }
    }
}

@Composable
fun ViewPointCard(viewPoint: ViewPoint) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top // 保持内容顶部对齐
        ) {
            // 调整后的图片部分
            Image(
                painter = painterResource(id = viewPoint.imageRes),
                contentDescription = viewPoint.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 120.dp, height = 160.dp) // 更高的长方形比例
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            // 文本和按钮部分
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = viewPoint.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = viewPoint.description,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: Add navigation logic */ },
                    modifier = Modifier.align(Alignment.End),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB497BD)
                    )
                ) {
                    Text(text = "More", fontSize = 14.sp)
                }
            }
        }
    }
}

fun generateViewPoints(): List<ViewPoint> {
    return listOf(
        ViewPoint(
            "Yellow Crane Tower",
            "G8V2+RW5, Huanghelou E Rd, Wuchang District, Wuhan, Hubei, China, 430060",
            R.drawable.hh6 // 图片 1
        ),
        ViewPoint(
            "Wuhan University",
            "G9P7+CP8, Wuchang District, Wuhan, Hubei, China, 430072",
            R.drawable.sa2 // 图片 2
        ),
        ViewPoint(
            "Linbo Gate",
            "China, Hubei, Wuhan, Wuchang District, 299, 430074",
            R.drawable.lb2 // 图片 3
        ),
        ViewPoint(
            "Wuhan Yangtze River Bridge",
            "Built in 1957, this famed double-deck road & rail truss bridge crosses the Yangtze River.",
            R.drawable.dq1 // 图片 4
        )
    )
}




