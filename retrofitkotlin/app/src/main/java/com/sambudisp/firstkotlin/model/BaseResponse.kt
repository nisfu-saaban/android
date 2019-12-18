package com.sambudisp.firstkotlin.model

import com.google.gson.annotations.SerializedName

//T singkatan dari Type yang artinya sama Any
data class BaseResponse<T> (
        @field:SerializedName("status") //SerializedName ini disamakan dengan data di JSON, kata lainnya sebagai Alias.
        val status: String? = null,

        @field:SerializedName("data")
        val data: T? = null,

        @field:SerializedName("error")
        val error: String? = null
)
