package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class DashboardActivity : AppCompatActivity() {
    private lateinit var Bookfutsal: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Bookfutsal = findViewById(R.id.Bookfutsal)

        Bookfutsal.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, Bookfutsal::class.java))
        }
    }
}