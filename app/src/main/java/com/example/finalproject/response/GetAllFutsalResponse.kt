package com.example.finalproject.response

import com.example.finalproject.entity.Futsal

data class GetAllFutsalResponse (
    val success : Boolean? = null,
    val count : Int? = null,
    val data : MutableList<Futsal>? = null
)