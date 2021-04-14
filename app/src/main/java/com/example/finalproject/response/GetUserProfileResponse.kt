package com.example.finalproject.response

import com.example.finalproject.entity.Futsal
import com.example.finalproject.entity.User

data class GetUserProfileResponse (
    val success: Boolean? = null,
    val data: User? = null
)
