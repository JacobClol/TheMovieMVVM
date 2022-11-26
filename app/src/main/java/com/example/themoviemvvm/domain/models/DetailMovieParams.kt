package com.example.themoviemvvm.domain.models

data class DetailMovieParams(
    val idMovie: Int,
    val apiKey: String,
    val language: String? = null
)
