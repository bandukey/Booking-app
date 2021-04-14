package com.example.finalproject.repository

import com.example.finalproject.API.MyApiRequest
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.API.UserAPI
import com.example.finalproject.entity.User
import com.example.finalproject.response.GetUserProfileResponse
import com.example.finalproject.response.ImageResponse
import com.example.finalproject.response.LoginResponse
import com.example.finalproject.response.UpdateUserResponse
import okhttp3.MultipartBody

class UserRepository :
    MyApiRequest(){
    private val UserAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //Register User

    suspend fun registerUser(user: User) : LoginResponse {
        return apiRequest {
            UserAPI.registerUser(user)
        }
    }
    //Login User
    suspend fun loginUser(email : String,password : String) : LoginResponse {
        return apiRequest {
            UserAPI.checkUser(email,password)
        }
    }

    suspend fun userImageUpload(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            UserAPI.userImageUpload(id, body)
        }
    }

    suspend fun getMe(): GetUserProfileResponse {
        return apiRequest {
            UserAPI.getMe(ServiceBuilder.token!!)
        }
    }

    suspend fun updateUser(id:String, user: User ): UpdateUserResponse {
        return apiRequest {
            UserAPI.updateUser(id, user)
        }
    }

}