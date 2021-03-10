package com.example.finalproject.API

import com.example.finalproject.response.ImageResponse

interface FutsalAPI {
    @POST("newsfeed/")
    suspend fun addnews(
        @Header("Authorization") token: String,
        @Body newsfeed: newsfeed
    ): Response<AddFeedResponse>

    @GET("newsfeed/")
    suspend fun getAllNews(
        @Header("AUthorization") token: String

    ): Response<GetAllNewsResponse>

    @Multipart
    @PUT("newsfeed/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}