package com.example.finalproject.response

import com.example.finalproject.entity.User

data class UpdateUserResponse (
    val success: Boolean? = null,
    val data: User? = null
)
