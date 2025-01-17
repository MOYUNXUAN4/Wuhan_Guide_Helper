package com.example.wuhan_guide_helper.user

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wuhan_guide_helper.MainScreenActivity
import com.example.wuhan_guide_helper.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun UserDetail(email: String, username: String) {
    val context = LocalContext.current
    val auth = Firebase.auth

    // 加载自定义字体
    val tianOneFontFamily = FontFamily(
        Font(R.font.sigmaroneregular) // 确保资源 ID 正确
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User\nDetails", // 使用 \n 将文本分成两行
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = tianOneFontFamily,
                fontSize = 80.sp,
                lineHeight = 100.sp // 设置行高为 100sp，增加两行之间的间距
            ),
            textAlign = TextAlign.Center, // 设置文本居中对齐
            modifier = Modifier
                .fillMaxWidth() // 让 Text 组件占据整个宽度
                .padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 显示用户邮箱
                Text(
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 显示用户名
                Text(
                    text = "Username: $username",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 登出按钮
        Button(
            onClick = {
                auth.signOut() // 登出
                val intent = Intent(context, MainScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent) // 跳转到主界面
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0000) // 按钮背景颜色改为红色 (#FF0000)
            )
        ) {
            Text(
                text = "Logout",
                color = Color.White // 文字颜色保持白色
            )
        }
    }
}