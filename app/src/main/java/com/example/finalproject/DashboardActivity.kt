package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class DashboardActivity : AppCompatActivity() {
    private lateinit var Bookfutsal: ImageView
    private lateinit var addFutsal: ConstraintLayout
    private lateinit var viewFutsal: ConstraintLayout
    private lateinit var viewProfile: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Bookfutsal = findViewById(R.id.Bookfutsal)
        addFutsal = findViewById(R.id.addFutsal)
        viewFutsal = findViewById(R.id.viewFutsal)
        viewProfile = findViewById(R.id.viewProfile)

        Bookfutsal.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, BookFutsalActivity::class.java))
        }

        addFutsal.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, AddFutsalActivity::class.java))
        }

        viewFutsal.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ViewFutsalsActivity::class.java))
        }

        viewProfile.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java))
        }

    }
}