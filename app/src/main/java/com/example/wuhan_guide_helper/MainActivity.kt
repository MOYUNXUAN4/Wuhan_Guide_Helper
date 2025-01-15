package com.example.wuhan_guide_helper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}



