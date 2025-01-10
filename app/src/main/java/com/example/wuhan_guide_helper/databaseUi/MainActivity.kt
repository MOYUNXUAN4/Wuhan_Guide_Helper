package com.example.wuhan_guide_helper.databaseUi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.wuhan_guide_helper.MainScreenActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}



