package com.example.finalproject.response

import com.example.finalproject.entity.User

data class   LoginResponse (
        val success :Boolean? = null,
        val token :String? = null,
        val data :User? = null
)

