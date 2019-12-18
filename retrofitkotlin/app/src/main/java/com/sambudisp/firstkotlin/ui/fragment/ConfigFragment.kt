package com.sambudisp.firstkotlin.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sambudisp.firstkotlin.FirstKotlinApp

import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.model.StudentsRequest
import com.sambudisp.firstkotlin.model.StudentsResponse
import com.sambudisp.firstkotlin.ui.activity.StudentsDetailActivity2
import com.sambudisp.firstkotlin.ui.adapter.ConfigAdapter
import kotlinx.android.synthetic.main.fragment_config.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigFragment : Fragment() {

    companion object {
        const val ID = "id"
    }

    private val listStudent = mutableListOf<StudentsRequest>()
    private lateinit var adapterMain : ConfigAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_config, container, false)

        initRecyclerView()
        requestServices()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterMain = ConfigAdapter(listStudent) {
            val goStudentDetail2 = Intent(context, StudentsDetailActivity2::class.java)
            goStudentDetail2.putExtra(ID, it)
            startActivity(goStudentDetail2)
        }
    }

    private fun initRecyclerView() {
        with(rv_config) {
            adapter = adapterMain
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun requestServices() {
        FirstKotlinApp.apiServices
        .getAllStudents2()
        .enqueue(object : Callback<StudentsResponse>{
            override fun onFailure(call: Call<StudentsResponse>, t: Throwable) {
                pbar_config.visibility = View.GONE
            }

            override fun onResponse(call: Call<StudentsResponse>, response: Response<StudentsResponse>) {
                pbar_config.visibility = View.GONE
                if (response.code() == 200) {
                    response.body()?.data?.let {
                        getData(it.toMutableList())
                    }
                }
            }
        })
    }

    private fun getData(data: MutableList<StudentsRequest>) {
        listStudent.clear()
        listStudent.addAll(data)
        adapterMain.notifyDataSetChanged()
    }


}
