package com.example.wuhan_guide_helper.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wuhan_guide_helper.ui.theme.Wuhan_Guide_HelperTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class UserDetailActivity : ComponentActivity() {
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (auth.currentUser == null) {
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