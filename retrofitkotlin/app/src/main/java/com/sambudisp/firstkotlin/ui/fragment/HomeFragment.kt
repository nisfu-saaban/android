package com.sambudisp.firstkotlin.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sambudisp.firstkotlin.FirstKotlinApp
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.request
import com.sambudisp.firstkotlin.model.StudentsRequest
import com.sambudisp.firstkotlin.ui.activity.StudentsDetailActivity
import com.sambudisp.firstkotlin.ui.adapter.AdapterStudents
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    companion object {
        const val STUDENT = "student"
    }

    private val api = FirstKotlinApp.apiServices

    private val studentsList = mutableListOf<StudentsRequest>()
    private lateinit var studentAdapter: AdapterStudents

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentAdapter = AdapterStudents(studentsList) {
            val goStudentDetail = Intent(context, StudentsDetailActivity::class.java)
            goStudentDetail.putExtra(STUDENT, it) //digunakan untuk mengirimkan data ke act tujuan, mau diapain nantinya terserah di act tujuan
            startActivity(goStudentDetail)
        }

        view.rvStudents.layoutManager = LinearLayoutManager(context) //Layout Manager digunakan untuk mengatur bentuk Recycler Viewnya mau ke bawah atau ke samping. Disini kita buat defaultnya yaitu Vertical
        /**
         * 0. Vertical : kayak koding di atas ^
         * 1. Horizontal : view.rvStudents.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL) //Ini kalau mau pakai yang HORIZONTAL
         * 2. Grid : view.rvStudents.layoutManager = GridLayoutManager(context, 3) //Ini kalau mau pakai GRID kayak di profil Instagram, disini kita menentukan 3 kolom ke kanan
         * **/
        view.rvStudents.adapter = studentAdapter

        /* //Bisa juga menggunakan penulisan gaya kotlin berikut
        view.rvStudents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = studentAdapter
        }*/
    }

    override fun onResume() {
        super.onResume()
        studentsList.clear()
        getStudentsData()
    }

    //Nantinya data JSON kita masukan ke sini, sekarang kita pakai dummy dulu
    private fun getStudentsData() {
        //pilih Callback yang retrofit2
        /*api.getAllStudents().enqueue(object : Callback<BaseResponse<List<StudentsRequest>>>{
            override fun onFailure(call: Call<BaseResponse<List<StudentsRequest>>>, t: Throwable) {
                //disini menginformasikan pesan error kalau proses request gagal (ga ada kuota, dll), "t" untuk mengetahui errornya
                showMessage("Request Gagal")
            }

            override fun onResponse(call: Call<BaseResponse<List<StudentsRequest>>>, response: Response<BaseResponse<List<StudentsRequest>>>) {
                //disini menginformasikan hasil data yang berhasil diambil
                if (response.body() != null){
                    //untuk menyimpan data Student dari response/
                    val dataStudents = response.body()?.data
                    dataStudents?.let {
                        studentsList.addAll(it)
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }

        })*/

        //cara 2 menampilkan data dan menampilkan progressbar/loading saat data masih diload
        showProgressBar(true)
        api.getAllStudents()
                .request({
                    showMessage("Gagal Request")
                    showProgressBar(false)
                }, { response ->
                    /* `dataStudents` : untuk menyimpan data students dari response*/
                    showProgressBar(false)
                    val dataStudents = response?.data
                    dataStudents?.let {
                        studentsList.addAll(it)
                        studentAdapter.notifyDataSetChanged()
                    }
                })


        /* //dibawah ini adalah contoh data dummy Statis
        studentsList.add(StudentsRequest(
                idnya = 1,
                namanya = "Sambudi Sugya Putra AAA",
                emailnya = "sambudispx@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 2,
                namanya = "Adhi Dummy 2",
                emailnya = "adhi@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 3,
                namanya = "Sans Ae Dummy 3",
                emailnya = "sans@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 4,
                namanya = "Dummy Empat",
                emailnya = "dummy4@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 5,
                namanya = "Dummy 5",
                emailnya = "dummy5@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 6,
                namanya = "Dummy 6",
                emailnya = "dummy@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 7,
                namanya = "Dummy 7",
                emailnya = "dummy5@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 8,
                namanya = "Dummy 8",
                emailnya = "dummy@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 9,
                namanya = "Dummy 9",
                emailnya = "dummy@gmail.com",
                avatarnya = R.drawable.avatar
        ))
        studentsList.add(StudentsRequest(
                idnya = 10,
                namanya = "Dummy 10",
                emailnya = "dummy@gmail.com",
                avatarnya = R.drawable.avatar
        ))

        studentAdapter.notifyDataSetChanged() //memberitahu ke adapter bahwa ada data masuk / ada perubahan data di studentAdapter. Nantinya disini data akan diload ulang jika ada perubahan*/
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showProgressBar(show: Boolean) {
        view?.progressBarnya?.visibility = if (show) View.VISIBLE else View.GONE
    }
}
