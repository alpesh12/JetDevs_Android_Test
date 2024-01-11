package com.imaginato.homeworkmvvm.ui.login.Entity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.databinding.ActivityMainBinding
import com.imaginato.homeworkmvvm.ui.demo.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickView()
    }

    private fun clickView() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (etUsername.text.isNullOrBlank()) {
                    Toast.makeText(this@LoginActivity,"Please Enter Email", Toast.LENGTH_SHORT).show()
                } else if (etPassword.text.isNullOrBlank()) {
                    Toast.makeText(this@LoginActivity,"Please Enter Password", Toast.LENGTH_SHORT).show()
                } else {
                    //Api Call
                }

            }
        }
    }
}