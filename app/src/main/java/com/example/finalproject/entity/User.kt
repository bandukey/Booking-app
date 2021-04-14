package com.example.finalproject.entity

import androidx.room.PrimaryKey

data class User (
        @PrimaryKey
    val _id : String? = null,
        val username : String? = null,
        val email : String? = null,
        val phoneNo : String? = null,
        val password : String? = null,
        val photo : String? = null
)