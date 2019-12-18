package com.sambudisp.firstkotlin.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.data.PreferenceHelper
import com.sambudisp.firstkotlin.data.PreferenceKey
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var pref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pref = PreferenceHelper(this)//preference diinisialisasi dulu
        cekStatusLogin()

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun cekStatusLogin() {
        val hasLogin = pref.getBoolean(PreferenceKey.HAS_LOGIN)
        if (hasLogin) {
            goHomePage()
        }
    }

    private fun simpanDataLogin(username: String) {
        pref.putString(PreferenceKey.USERNAME, username)
        pref.putBoolean(PreferenceKey.HAS_LOGIN, true)
    }

    private fun goHomePage() {
        val goHome = Intent(this, HomeActivity::class.java)
        startActivity(goHome)
        finish()
    }

    fun login() {
        val username = edtUsername.text.toString()
        val password = edtPassword.text.toString()

        //isBlank() : spasi tidak dihitung, isEmpty : spasi tetap dihitung ada
        if (username.isBlank() || password.isBlank()) { // bisa juga menggaunakan username == "" tapi lebih baik pakai isBlack(), bisa juga mennggunakan isNotBlank() tergantung logic kita masing2
            Toast.makeText(this, "username dan password belum diisi", Toast.LENGTH_LONG).show()
            println("berhasil")
        } else {
            //mengakses sharedPreferencenya untuk menyimpan data login (username dan status login)
            simpanDataLogin(username)
            goHomePage()
        }

    }
}
