package com.imaginato.homeworkmvvm.ui.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.data.local.login.DemoDatabase
import com.imaginato.homeworkmvvm.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    lateinit var db: DemoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DemoDatabase.getInstance(this)

        val user =  db.demoDao().getAllUsers()

        binding.apply {
            if(user.name !== null) {textUser.setText("Welcome, "+user.name)}
        }
    }
}