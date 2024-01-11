package com.imaginato.homeworkmvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
            // Login button click event
            btnLogin.setOnClickListener {
                var isError = false
                // Check for Validation
                if (etUsername.text.isNullOrBlank()) {
                    etUsername.setBackgroundResource(R.drawable.edit_error_bg);
                    txtEmailError.visibility= View.VISIBLE
                    isError = true
                }
                if (etPassword.text.isNullOrBlank()) {
                    etPassword.setBackgroundResource(R.drawable.edit_error_bg);
                    txtPasswordError.visibility= View.VISIBLE
                    isError = true
                }else if(etPassword.text!!.length < 8){
                    txtPasswordError.setText("Password must be of 8 character")
                    txtPasswordError.visibility= View.VISIBLE
                    isError = true
                }
                if(!isError){
                    etUsername.setBackgroundResource(R.drawable.edt_white_bg)
                    etPassword.setBackgroundResource(R.drawable.edt_white_bg)
                    // Login Api call
                    callLoginApi()
                }
            }

            // Username text change event
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
                    txtEmailError.visibility= View.GONE
                    if (count > 0) {
                        etUsername.setBackgroundResource(R.drawable.edt_white_bg)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })

            // Password text change event
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
                    txtPasswordError.visibility= View.GONE
                    if (count > 0) {
                        etPassword.setBackgroundResource(R.drawable.edt_white_bg)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })
        }
    }

    // Observer observes live data of Login data and X-acc and update ui
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

    // Login api call
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
