package com.sambudisp.firstkotlin.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sambudisp.firstkotlin.FirstKotlinApp
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.StudentsRequest
import com.sambudisp.firstkotlin.request
import com.sambudisp.firstkotlin.toast
import com.sambudisp.firstkotlin.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_add_show_data.*
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    private var student: StudentsRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val student: StudentsRequest? = intent?.getParcelableExtra(HomeFragment.STUDENT)
        this.title = getString(R.string.profil_student)
        Toast.makeText(this, "Terpilih : ${student?.namanya}", Toast.LENGTH_LONG).show()

        setupView()
    }

    private fun setupView() {
        btnUpdate.setOnClickListener {
            updateDataStudent()
        }
    }

    private fun updateDataStudent() {
        val nama = edtNama.text.toString()
        val email = edtEmail.text.toString()

        if (nama.isBlank() || email.isBlank()) {
            toast("Nama dan Email tidak boleh kosong")
            return //untuk membunuh function / mengehentikan proses sampai sini, biar tidak dilanjut ke proses selanjutnya
        }

        //eksekuasi save data ke API
        val map = mutableMapOf<String, String>()
        map["name"] = nama
        map["email"] = email
        showProgressBar(true)
        FirstKotlinApp.apiServices.updateStudent(student?.idnya.toString())
                .request({
                    toast("Gagal Update Data, Cek Koneksi")
                    showProgressBar(false)
                }, {
                    showProgressBar(false)
                    if (it != null){
                        val student = it.data
                        toast("Data siswa ${student?.namanya} telah terupdate")
                    }else {
                        toast("Gagal Mengupdate Data")
                    }

                })

    }

    fun showProgressBar(show: Boolean) {
        progressBarnya.visibility = if (show) View.VISIBLE else View.GONE
    }

    //tombol back
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            val goHome = Intent(this, HomeActivity::class.java)
            startActivity(goHome)
            finish()
        } //ga pake kurung kurawal karena cuma sebaris (pakai jg gpp), kalau lebih dari sebaris pakai kurung kurawal
        return super.onOptionsItemSelected(item)
    }
}
