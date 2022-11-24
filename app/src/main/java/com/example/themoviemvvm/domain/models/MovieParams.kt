package com.example.themoviemvvm.domain.models

data class MovieParams (
    val page: Int? = 1,
    val apiKey: String,
    val language: String? = null,
    val region: String? = null
)