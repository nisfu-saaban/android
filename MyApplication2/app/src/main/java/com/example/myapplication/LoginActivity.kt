package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        tv_signup.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
        bt_signin.setOnClickListener { userLoginIn() }

    }

    private fun userLoginIn() {
        val email = et_email!!.text.toString()
        val password = et_password!!.text.toString()

        when {
            TextUtils.isEmpty(email) ->
                et_email!!.error = "Enter email address!"

            TextUtils.isEmpty(password) ->
                et_password!!.error = "Enter password!"

            else -> {
                pg_login!!.visibility = View.VISIBLE

                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        pg_login!!.visibility = View.GONE
                        if (!task.isSuccessful) {
                            if (password.length < 6) {
                                et_password!!.error =
                                    "Password too short, enter minimum 6 characters!"
                            } else {
                                et_password!!.error =
                                    "Authentication failed, Check your Email and Password or Sign Up"
                            }
                        } else {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            }
        }
    }
}
