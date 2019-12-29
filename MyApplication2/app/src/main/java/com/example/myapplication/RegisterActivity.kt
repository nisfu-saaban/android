package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tv_signin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
        bt_signup.setOnClickListener { userRegister() }
    }

    private fun userRegister() {
        val username = et_regist_username!!.text.toString().trim { it <= ' ' }
        val email = et_regist_email!!.text.toString().trim { it <= ' ' }
        val password = et_regist_password!!.text.toString().trim { it <= ' ' }

        when {
            TextUtils.isEmpty(username) ->
                et_regist_username!!.error = "Enter Username!"
            TextUtils.isEmpty(email) ->
                et_regist_email!!.error = "Enter email address!"
            TextUtils.isEmpty(password) ->
                et_regist_password!!.error = "Enter password!"

            password.length < 6 ->
                et_regist_password!!.error = "Password too short, enter minimum 6 characters!"

            else -> {
                pg_regis!!.visibility = View.VISIBLE

                auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        Toast.makeText(
                            this,
                            "Account Created. Here you go to next activity." + task.isSuccessful,
                            Toast.LENGTH_SHORT
                        ).show()
                        pg_regis!!.visibility = View.GONE

                        if (!task.isSuccessful) {
                            Toast.makeText(
                                this, "Authentication failed." + task.exception!!,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
            }
        }
    }
}
