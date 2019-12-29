package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.Equalizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.example.myapplication.model.DataItem
import com.google.android.gms.location.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.jar.Manifest

class DetailActivity : AppCompatActivity() {

    val PREMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val extra = intent.extras

//        if (extra != null){
//            onBindData(extra,dataItem)
//        }else{
//            finish()
//        }
        getParsingData()
        getLastLocation()
    }

    private fun getParsingData() {
        if(intent.extras != null){
            val bundle = intent?.extras
            try {
                val img = bundle?.getString(MainActivity.IMAGE).toString()
                Picasso.get().load(img).fit().into(iv_desc)

                tv_des_title.text = bundle?.getString(MainActivity.TITLE).toString()
                tv_des_description.text = bundle?.getString(MainActivity.DETAIL)
                tv_des_addres.text = bundle?.getString(MainActivity.ADDRESS)
            }catch (e: Exception){
                toast("This is try : ${e.message}")
            }
        }else{
            toast("Null Bos")
        }
    }

    @SuppressLint("MissingPremission")
    private fun getLastLocation(){
        if (checkPremission()){
            if (isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){
                    task ->
                    var location: Location? = task.result
                    if (location == null){
                        requestNewLocationData()
                    }else{
                        tv_latitude.text = location.latitude.toString()
                        tv_longitude.text = location.longitude.toString()
                    }
                }
            }else{
                toast("Tur on location")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPremission()
        }
    }

    @SuppressLint("MissingPremission")
    private fun requestNewLocationData(){
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object  : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var mLastLocation: Location = p0.lastLocation
            tv_latitude.text = mLastLocation.latitude.toString()
            tv_longitude.text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPremission(): Boolean{
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPremission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),PREMISSION_ID)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PREMISSION_ID){
            if ((grantResults.isEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                getLastLocation()
            }
        }
    }
}
