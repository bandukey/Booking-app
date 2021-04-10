package com.example.finalproject.API
import com.example.finalproject.entity.user
import com.example.finalproject.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {

    @POST("register")
    suspend fun  registerUser(
        @Body user: user
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("user/login")
    suspend fun checkUser(
        @Field("email") email:String,
        @Field("password") password:String


    ):Response<LoginResponse>}