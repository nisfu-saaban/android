package com.sambudisp.firstkotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sambudisp.firstkotlin.R
import kotlinx.android.parcel.Parcelize

//Class ini akan digunakan untuk memilih data apa saja yang ingin ditampilkan
@Parcelize
data class StudentsRequest(
        @field:SerializedName("id") //nama variabel di bawah harus sama dengan field di JSONnya, kalau misal beda, kita atasi dengan cara ini. Di contoh ini nama variabelku beda ya, makanya ini dibuat
        val idnya: Int? = null,

        @field:SerializedName("name")
        val namanya: String? = null,

        @field:SerializedName("email")
        val emailnya: String? = null,

        val avatarnya: Int? = R.drawable.avatar //ini contoh aja pakai "Int" karena ngambilnya dari Drawable (ngambil IDnya)
) : Parcelable //Biar bisa kirim2 data ke act lain