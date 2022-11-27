package com.example.themoviemvvm.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> Gson.fromJson(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)