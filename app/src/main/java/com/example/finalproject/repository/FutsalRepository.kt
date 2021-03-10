package com.example.finalproject.repository

import com.example.finalproject.API.MyApiRequest
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.entity.futsal
import com.example.finalproject.response.AddFutsalResponse
import com.example.finalproject.response.GetAllFutsalResponse
import com.example.finalproject.response.ImageResponse

class FutsalRepository:
    MyApiRequest() {
    private val NewsfeedAPI =
        ServiceBuilder.buildService(NewsfeedAPI::class.java)

    //Add student

    suspend fun addfutsal (newsfeed: futsal): AddFutsalResponse{
        return apiRequest {
            NewsfeedAPI.addnews(
                ServiceBuilder.token!!, newsfeed
            )
        }
    }
    suspend fun getAllFutsal(): GetAllFutsalResponse {
        return apiRequest {
            NewsfeedAPI.getAllNews(ServiceBuilder.token!!)

        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            NewsfeedAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

}}