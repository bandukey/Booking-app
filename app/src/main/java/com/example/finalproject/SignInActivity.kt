package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.repository.FutsalRepository
import com.example.finalproject.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignInActivity : AppCompatActivity() {

    private lateinit var btnsignin: Button
    private lateinit var btnregister: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var rootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnsignin = findViewById(R.id.btnsignin)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnregister = findViewById(R.id.btnregister)

        btnsignin.setOnClickListener {
            validatelogindetails()
            login()
        }

    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(email, password)
                if (response.success == true) {

                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                            Intent(
                                    this@SignInActivity,
                                    DashboardActivity::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        rootLayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK") {
                            snack.dismiss()
                        }
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                            this@SignInActivity,
//                            ex.toString(), Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
    }

    private fun validatelogindetails(): Boolean {
        if(etEmail.text.toString().isEmpty()){
            etEmail.error = "Blank Email"
            etEmail.requestFocus()
            return false
        }
        else if(etPassword.text.toString().isEmpty()){
            etPassword.error = "Blank Password"
            etPassword.requestFocus()
            return false
        }
        return true
    }
    }