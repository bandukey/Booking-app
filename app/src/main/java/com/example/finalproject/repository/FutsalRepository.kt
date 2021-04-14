package com.example.finalproject.repository

import com.example.finalproject.API.FutsalAPI
import com.example.finalproject.API.MyApiRequest
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.entity.Futsal
import com.example.finalproject.response.AddFutsalResponse
import com.example.finalproject.response.GetAllFutsalResponse
import com.example.finalproject.response.ImageResponse
import okhttp3.MultipartBody

class FutsalRepository:
    MyApiRequest() {
    private val FutsalAPI =
        ServiceBuilder.buildService(FutsalAPI::class.java)

    //Add student

    suspend fun addfutsal (futsal: Futsal): AddFutsalResponse{
        return apiRequest {
            FutsalAPI.addfutsal(
                ServiceBuilder.token!!, futsal
            )
        }
    }
    suspend fun getAllfutsal(): GetAllFutsalResponse {
        return apiRequest {
            FutsalAPI.getAllfutsal(ServiceBuilder.token!!)

        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            FutsalAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

}