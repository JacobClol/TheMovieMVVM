package com.example.themoviemvvm.core.utils

import android.util.Log
import kotlin.properties.Delegates

object NetworkState {
    var isNetworkConnected: Boolean by Delegates.observable(false){
        property, oldValue, newValue -> Log.i("Network connectivity", "$newValue")
    }
}