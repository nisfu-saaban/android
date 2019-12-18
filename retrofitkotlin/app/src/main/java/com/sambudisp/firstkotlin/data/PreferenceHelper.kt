package com.sambudisp.firstkotlin.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val preference: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences("binar-batch-9", Context.MODE_PRIVATE)
    }

    //membuat fungsi untuk menyimpan data sesuai tipe data yang ingin kita simpan
    fun putString(key: String, value: String?) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(key, value).apply() //beda penulisan aja dikit sama yang di atas
    }

    //membuat fungsi untuk membaca data
    fun getString(key: String): String? = preference.getString(key, null)
    fun getBoolean(key: String): Boolean = preference.getBoolean(key, false)

    //untuk mengClear data preference
    fun logout(){
        val editor = preference.edit()
        editor.clear().apply()
    }
}