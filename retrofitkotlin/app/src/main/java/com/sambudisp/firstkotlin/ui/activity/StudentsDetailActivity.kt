package com.sambudisp.firstkotlin.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sambudisp.firstkotlin.FirstKotlinApp
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.StudentsRequest
import com.sambudisp.firstkotlin.request
import com.sambudisp.firstkotlin.toast
import com.sambudisp.firstkotlin.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_students_detail.*

class StudentsDetailActivity : AppCompatActivity() {

    private var student: StudentsRequest? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val student: StudentsRequest? = intent?.getParcelableExtra(HomeFragment.STUDENT)
        this.title = getString(R.string.profil_student)
        Toast.makeText(this, "Terpilih : ${student?.namanya}", Toast.LENGTH_LONG).show()

        getSpesificStudent(student?.idnya.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }


    //tombol back dan menutoolbar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        } else if (item?.itemId == R.id.deleteStudent) {
            showDeleteConfimaton()
        } else if (item?.itemId == R.id.editStudent) {
            goUpdateStudent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goUpdateStudent() {
        val goUpdateStudent = Intent(this, UpdateActivity::class.java)
        startActivity(goUpdateStudent)
        finish()
    }


    private fun getSpesificStudent(id: String?) {
        FirstKotlinApp.apiServices.getSpesificStudent(id)
                .request({
                    toast("Gagal Ambil Data")
                }, {
                    if (it != null) {
                        student = it.data
                        showStudent(student)
                    } else {
                        toast("Gagal ambil data, server error?")
                    }
                })
    }

    //memunculkan data di komponen tampilan
    private fun showStudent(student: StudentsRequest?) {
        student?.avatarnya?.let {
            imgAvatar.setImageResource(it)
        }
        txtNama.text = student?.namanya
        txtEmail.text = student?.emailnya

        menuInflater.inflate(R.menu.menu_delete_edit_detail_student, menu) //membuat load tombol setelah berhasil memproses data
    }


    private fun showDeleteConfimaton() {
        AlertDialog.Builder(this) //pilih yang v7
                .setMessage("Anda yakin akan menghapus siswa ${student?.namanya} ")
                .setPositiveButton("Ya") { dialog, which ->
                    deleteStudent()
                    dialog.dismiss()
                }
                .setNegativeButton("Tidak") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false) //untuk mengharuskan user klik tombol di dialog, kalau true maka klik sembarang dialog bisa ilang
                .show()
    }

    private fun deleteStudent() {
        FirstKotlinApp.apiServices.deleteStudent(student?.idnya.toString())
                .request({
                }, {
                    if (it != null) {
                        toast("Data siswa ${student?.namanya} berhasi dihapus ")
                        finish()
                    } else {
                        toast("Gagal menghapus data")
                    }
                })
    }

}
