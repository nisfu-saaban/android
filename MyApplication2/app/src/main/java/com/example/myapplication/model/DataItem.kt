package com.example.myapplication.model

import com.google.gson.annotations.SerializedName


data class DataItem(

    @field:SerializedName("gambar_pariwisata")
    val gambarPariwisata: String? = null,

    @field:SerializedName("nama_pariwisata")
    val namaPariwisata: String? = null,

    @field:SerializedName("alamat_pariwisata")
    val alamatPariwisata: String? = null,

    @field:SerializedName("detail_pariwisata")
    val detailPariwisata: String? = null
)