package com.example.finalproject.entity

import androidx.room.PrimaryKey

data class user (
    @PrimaryKey
    val _id : String? = null,
    val name : String? = null,
    val email : String? = null,
    val phoneNo: String? = null,
    val password : String? = null
)