package com.example.themoviemvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviemvvm.R
import com.example.themoviemvvm.core.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TheMovieMVVM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkMonitor().startNetworkCallback(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkMonitor().stopNetworkCallback(this)
    }

}