package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ResponseModel(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null
)