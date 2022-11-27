package com.example.themoviemvvm.core.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build

class NetworkMonitor {
    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            NetworkState.isNetworkConnected = true
        }

        override fun onLost(network: Network) {
            NetworkState.isNetworkConnected = false
        }
    }

    fun startNetworkCallback(activity: Activity){
        val cm: ConnectivityManager = activity.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    fun stopNetworkCallback(activity: Activity){
        val cm: ConnectivityManager = activity.application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }
}