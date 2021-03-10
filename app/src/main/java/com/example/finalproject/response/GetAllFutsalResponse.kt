package com.example.finalproject.response

import com.example.finalproject.entity.futsal

class GetAllFutsalResponse (
    val success : Boolean? = null,
    val count : Int? = null,
    val data : MutableList<futsal>? = null
)