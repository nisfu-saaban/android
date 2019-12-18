package com.sambudisp.firstkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentsResponse(
        @SerializedName("status")
        val status: String? = null,

        @SerializedName("data")
        val data: List<StudentsRequest>? = null,

        @SerializedName("error")
        val error: String? = null
) : Parcelable
