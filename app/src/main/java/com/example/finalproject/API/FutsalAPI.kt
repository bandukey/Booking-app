package com.example.finalproject.API

import com.example.finalproject.entity.Futsal
import com.example.finalproject.response.AddFutsalResponse
import com.example.finalproject.response.GetAllFutsalResponse
import com.example.finalproject.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FutsalAPI {
    @POST("/add/futsal/")
    suspend fun addfutsal(
        @Header("Authorization") token: String,
        @Body futsal: Futsal
    ): Response<AddFutsalResponse>

    @GET("futsal/all")
    suspend fun getAllfutsal(
        @Header("AUthorization") token: String

    ): Response<GetAllFutsalResponse>

    @Multipart
    @PUT("futsal/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}