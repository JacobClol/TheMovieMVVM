package com.example.themoviemvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviemvvm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TheMovieMVVM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}