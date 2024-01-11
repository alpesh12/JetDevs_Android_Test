package com.imaginato.homeworkmvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.data.local.demo.Demo
import com.imaginato.homeworkmvvm.data.local.login.DemoDatabase
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.ui.demo.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import java.util.HashMap

@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    private val body = HashMap<String, Any>()

    private lateinit var db: DemoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickView()
        initObserve()
    }

    private fun clickView() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (etUsername.text.isNullOrBlank()) {
                    etUsername.setBackgroundResource(R.drawable.edit_error_bg);
                    Toast.makeText(this@LoginActivity, "Please Enter Email", Toast.LENGTH_SHORT)
                        .show()
                } else if (etPassword.text.isNullOrBlank()) {
                    etPassword.setBackgroundResource(R.drawable.edit_error_bg);
                    Toast.makeText(this@LoginActivity, "Please Enter Password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //Api Call
                    etUsername.setBackgroundResource(R.drawable.edt_white_bg)
                    etPassword.setBackgroundResource(R.drawable.edt_white_bg)
                    callLoginApi()
                }
            }

            etUsername.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (count > 0) {
                        etUsername.setBackgroundResource(R.drawable.edt_white_bg)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })

            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (count > 0) {
                        etPassword.setBackgroundResource(R.drawable.edt_white_bg)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })
        }
    }

    private fun initObserve() {
        viewModel.resultLiveData.observe(this, Observer {
            if (!it?.data?.userName.isNullOrBlank()) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })

        viewModel.headerData.observe(this@LoginActivity, Observer {
            lifecycleScope.launch {
                db = DemoDatabase.getInstance(this@LoginActivity)
                val user = Demo(name = binding.etUsername.text.toString(), token = it.toString())
                withContext(Dispatchers.IO) {
                    db.demoDao().insertDemo(user)
                }
                startActivity(
                    Intent(this@LoginActivity, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
                delay(1000)
            }
        })

        viewModel.progress.observe(this, Observer {
            binding.loadingBar.isVisible = it
        })
    }

    private fun callLoginApi() {
        binding.apply {
            body.apply {
                put("username", etUsername.text.toString())
                put("password", etPassword.text.toString())
            }
            viewModel.getLoginData(body)
        }
    }
}