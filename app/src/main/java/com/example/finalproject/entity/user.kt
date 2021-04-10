package com.example.finalproject.entity

import androidx.room.PrimaryKey

data class user (
        @PrimaryKey
    val _id : String? = null,
        val username : String? = null,
        val email : String? = null,
        val password : String? = null
)