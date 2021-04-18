package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.finalproject.response.NotificationChannels


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
            showHighPriorityNotification()
        }

        btnregisterpage.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
    }

    private fun showHighPriorityNotification() {
        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannels = NotificationChannels(this)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)

            .setContentTitle("High priority notification")
            .setContentText("This is my notification body")
            .setColor(Color.BLUE)
            .build()

        notificationManager.notify(1, notification)
    }


}