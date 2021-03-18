package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var btnloginpage: Button
    private lateinit var btnregisterpage: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnloginpage = findViewById(R.id.loginpage)
        btnregisterpage = findViewById(R.id.registerpage)


        btnloginpage.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
        }

        btnregisterpage.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
    }







}