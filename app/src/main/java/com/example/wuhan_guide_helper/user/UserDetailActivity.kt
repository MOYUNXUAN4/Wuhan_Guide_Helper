package com.example.wuhan_guide_helper.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth

class UserDetailActivity : ComponentActivity() {
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 检查用户是否已登录
        if (auth.currentUser == null) {
            // 用户未登录，跳转到登录界面
            val intent = Intent(this, UserSignIn::class.java)
            startActivity(intent)
            finish()
            return
        }

        val email = intent.getStringExtra("email") ?: ""
        val username = intent.getStringExtra("username") ?: ""

        setContent {
            Wuhan_Guide_HelperTheme {
                UserDetail(email = email, username = username)
            }
        }
    }
}