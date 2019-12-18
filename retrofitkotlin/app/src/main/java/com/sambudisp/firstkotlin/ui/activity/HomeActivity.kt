package com.sambudisp.firstkotlin.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sambudisp.firstkotlin.R
import com.sambudisp.firstkotlin.data.PreferenceHelper
import com.sambudisp.firstkotlin.ui.fragment.ConfigFragment
import com.sambudisp.firstkotlin.ui.fragment.HelpFragment
import com.sambudisp.firstkotlin.ui.fragment.HomeFragment
import com.sambudisp.firstkotlin.ui.fragment.ProfilFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile_dialog.*

class HomeActivity : AppCompatActivity() {

    private lateinit var pref: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        pref = PreferenceHelper(this)

        supportActionBar?.hide()
        setSupportActionBar(toolbar)


        setDrawer()
        homeJalan() //memunculkan fragment mana yang mau pertama diload, biasanya kan HOME dulu
        setupView()
    }

    private fun Context.toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun setDrawer() {
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawer_layoutnya,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                toast("Drawer opened")
            }
        }

        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layoutnya.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Set navigation view navigation item selected listener
        navigation_view.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.action_cut -> toast("Cut clicked")
                R.id.action_copy -> toast("Copy clicked")
                R.id.action_paste -> toast("Paste clicked")
                R.id.action_new -> {
                    // Multiline action
                    toast("New clicked")
                    drawer_layoutnya.setBackgroundColor(Color.RED)
                }

            }
            // Close the drawer
            drawer_layoutnya.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.toolbarMenuAdd) {
            val goAddShowData = Intent(this, AddShowDataActivity::class.java)
            startActivity(goAddShowData)
            finish()
            return super.onOptionsItemSelected(item)
        } else if (id == R.id.toolbarMenuLogout) {
            pref.logout()
            val goLogin = Intent(this, LoginActivity::class.java)
            startActivity(goLogin)
            finish()
            return super.onOptionsItemSelected(item)
        } else if (id == R.id.toolbarMenuProfile) {
            profileDialog()
            return super.onOptionsItemSelected(item)
        } else if (id == R.id.toolbarMenuProfile2) {
            profileDialog2()
            return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun profileDialog() {
        val myDialog = Dialog(this)
        myDialog.setContentView(R.layout.activity_profile_dialog)
        myDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }

    private fun profileDialog2() {
        val profileDialogView = LayoutInflater.from(this).inflate(R.layout.activity_profile_dialog, null)
        val dialogBuildernya = AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setView(profileDialogView)
        dialogBuildernya.show()
    }


    private fun setupView() {
        navBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    setupFragment(HomeFragment()) //tittle juga bisa dibuat melalui parameter disini
                    this.title = getString(R.string.home) //mengubah judul/tittle Action Bar sesuai yang kita inginkan, kita abil dari strings.xml aja
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navHelp -> {
                    setupFragment(HelpFragment())
                    this.title = getString(R.string.help)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navProfil -> {
                    setupFragment(ProfilFragment())
                    this.title = getString(R.string.profil)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navConfig -> {
                    setupFragment(ConfigFragment())
                    this.title = getString(R.string.config)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }


    }

    private fun homeJalan() {
        setupFragment(HomeFragment())
        this.title = getString(R.string.home)
    }

    private fun setupFragment(fragment: Fragment) { //pastikan disini import fragment yang v4
        //proses mereplace container (tumbal) dengan fragment yang kita ingin tampilkan saat menu navigasi diklik
        val transaksi: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaksi.replace(R.id.frameContainer, fragment) //frameContainer adalah IDnya container kita
        transaksi.addToBackStack(null)
        transaksi.commit()
    }
}
