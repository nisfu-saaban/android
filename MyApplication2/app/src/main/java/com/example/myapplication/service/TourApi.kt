package com.example.myapplication.service

import com.example.myapplication.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface TourApi {

    @GET("bootcamp/jsonBootcamp.php")
    fun getAllTour(): Call<ResponseModel>
}