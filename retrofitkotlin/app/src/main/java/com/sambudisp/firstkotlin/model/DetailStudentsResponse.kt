package com.sambudisp.firstkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailStudentsResponse(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: StudentsRequest? = null,

        @SerializedName("error")
        val error: String? = null
) : Parcelable