package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.MainAdapter
import com.example.myapplication.adapter.dataListener
import com.example.myapplication.model.DataItem
import com.example.myapplication.service.TourServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), dataListener {

    private val api = TourServices.apiServices
    private var authListener: FirebaseAuth.AuthStateListener? = null
    private var auth: FirebaseAuth? = null
    private var user: FirebaseUser? = null

    //lateinit var adapterMain: MainAdapter

    private val dataItem = mutableListOf<DataItem>()
    private val adapterMain = MainAdapter(dataItem,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_main_signout.setOnClickListener { onSignOut() }
        rv_main.layoutManager = LinearLayoutManager(this)

//        adapterMain = MainAdapter(dataItem){
//          tour ->
//            intentToDetail(tour)
//        }
        rv_main.adapter = adapterMain

        auth = FirebaseAuth.getInstance()
        user = FirebaseAuth.getInstance().currentUser

        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
        api.getAllTour()
            .request({
                toast("Gagal Mengambil Data")
            },{ response ->
                val itemData = response?.data
                itemData?.let {
                    dataItem.addAll(it)
                    adapterMain.notifyDataSetChanged()
                }
            })
    }


    private fun intentToDetail(dataItem: DataItem){
       // val intentDetail = Intent(this, DetailActivity::class.java)
//        intentDetail.putExtra(TITLE,dataItem.namaPariwisata)
//        intentDetail.putExtra(ADDRESS,dataItem.alamatPariwisata)
//        intentDetail.putExtra(DETAIL,dataItem.detailPariwisata)
//        intentDetail.putExtra(IMAGE,dataItem.gambarPariwisata)
        val bundle = Bundle()
        bundle.putString(TITLE,dataItem.namaPariwisata)
        bundle.putString(ADDRESS,dataItem.alamatPariwisata)
        bundle.putString(DETAIL,dataItem.detailPariwisata)
        bundle.putString(IMAGE,dataItem.gambarPariwisata)
        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtras(bundle)
        startActivity(intentDetail)
    }

    override fun onResume() {
        super.onResume()
        pg_main!!.visibility = View.GONE
    }

    public override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(authListener!!)
    }

    private fun onSignOut(){
        auth!!.signOut()
    }

    override fun onDataClick(dataItem: DataItem) {
        intentToDetail(dataItem)
    }

    companion object{
        const val TITLE = "namaPariwisata"
        const val ADDRESS = "alamatPariwisata"
        const val DETAIL = "detailPariwisata"
        const val IMAGE = "damgarPariwisata"
    }
}
