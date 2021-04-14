package com.example.finalproject.API
import com.example.finalproject.entity.User
import com.example.finalproject.response.GetUserProfileResponse
import com.example.finalproject.response.ImageResponse
import com.example.finalproject.response.LoginResponse
import com.example.finalproject.response.UpdateUserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("/register")
    suspend fun  registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //Login user
    @FormUrlEncoded
    @POST("/login")
    suspend fun checkUser(
        @Field("email") email:String,
        @Field("password") password:String
    ):Response<LoginResponse>

    @Multipart
    @PUT("/user/{id}/photo")
    suspend fun userImageUpload(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    @PUT("/update/user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body user:User
    ): Response<UpdateUserResponse>

    @GET("/me")
    suspend fun getMe(
        @Header ("Authorization") token: String
    ): Response<GetUserProfileResponse>
}