package com.example.themoviemvvm.domain.models

data class MovieParams (
    val page: Int?,
    val apiKey: String,
    val language: String?,
    val region: String?
)