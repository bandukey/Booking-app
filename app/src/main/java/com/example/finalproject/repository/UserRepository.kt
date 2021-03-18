package com.example.finalproject.repository

import com.example.finalproject.API.MyApiRequest
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.API.UserAPI
import com.example.finalproject.entity.user
import com.example.finalproject.response.LoginResponse

class UserRepository :
    MyApiRequest(){
    private val UserAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //Register User

    suspend fun registerUser(user: user) : LoginResponse {
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

}