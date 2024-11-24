package com.example.roomapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynote.database.NoteDao
import com.example.roomapplication.databinding.ActivityLoginBinding
import com.example.roomapplication.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        with(binding){
            btnLogin.setOnClickListener {
                val username = etUserLogin.text.toString()
                val password = etPassLogin.text.toString()
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Mohon isi semua data",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isValidUsernamePassword()) {
                        prefManager.setLoggedIn(true)
                        checkLoginStatus()
                    } else {
                        Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity,
                    RegisterActivity::class.java))
            }
        }
    }
    private fun isValidUsernamePassword(): Boolean{
        val username = prefManager.getUsername()
        val password = prefManager.getPassword()
        val inputUsername = binding.etUserLogin.text.toString()
        val inputPassword = binding.etPassLogin.text.toString()
        return username == inputUsername && password == inputPassword

    }
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (isLoggedIn) {
            Toast.makeText(
                this@LoginActivity, "Login berhasil",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
            )
            finish()
        } else {
            Toast.makeText(
                this@LoginActivity, "Login gagal",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}