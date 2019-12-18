package com.sambudisp.firstkotlin.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sambudisp.firstkotlin.FirstKotlinApp
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.DetailStudentsResponse
import com.sambudisp.firstkotlin.model.StudentsRequest
import kotlinx.android.synthetic.main.activity_students_detail2.*
import kotlinx.android.synthetic.main.fragment_config.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentsDetailActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_detail2)
        //requestService(intent.getStringExtra("id")
    }

    private fun requestService(id:String) {
        FirstKotlinApp.apiServices
                .getSpesificStudent2(id)
                .enqueue(object : Callback<DetailStudentsResponse> {
                    override fun onFailure(call: Call<DetailStudentsResponse>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<DetailStudentsResponse>, response: Response<DetailStudentsResponse>) {
                        if (response.code() == 200) {
                            response.body()?.data?.let {
                                getData(it)
                            }
                        }
                    }
                })

    }

    private fun getData(data: StudentsRequest?) {
        pbar_config.visibility = View.GONE
        txt_name_detail2.text = data?.namanya
        txt_email_detail2.text = data?.emailnya
    }

}
