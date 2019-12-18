package com.sambudisp.firstkotlin.networking

import com.sambudisp.firstkotlin.model.BaseResponse
import com.sambudisp.firstkotlin.model.DetailStudentsResponse
import com.sambudisp.firstkotlin.model.StudentsRequest
import com.sambudisp.firstkotlin.model.StudentsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    //ambilData
    @GET("api/v1/student/all")
    fun getAllStudents(): Call<BaseResponse<List<StudentsRequest>>> //pilih import yang retrofit2, jangan yang lain

    //simpanData
    @Headers("Content-Type:application/json") //hati2 dengan spasi
    @POST("api/v1/student/")
    abstract fun simpanData(@Body map: Map<String, String>): Call<BaseResponse<StudentsRequest>>

    //menampilkan data sesuai ID tertentu
    @GET("api/v1/student/{id}")
    abstract fun getSpesificStudent(@Path("id") id: String?): Call<BaseResponse<StudentsRequest>>

    //menghapus data tertentu
    @DELETE("api/v1/student/{id}")
    abstract fun deleteStudent(@Path("id") id: String?): Call<BaseResponse<StudentsRequest>>

    @PUT("/api/v1/student/{id}")
    abstract fun updateStudent(@Path("id") id: String?): Call<BaseResponse<StudentsRequest>>

    ///////////////////////////////////

    @GET("api/v1/student/{id}")
    abstract fun getSpesificStudent2(@Path("id") id: String?): Call<DetailStudentsResponse>

    @GET("api/v1/student/all")
    fun getAllStudents2(): Call<StudentsResponse>
}