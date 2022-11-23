package com.example.themoviemvvm.domain.models

data class Movie(
    val id: Int,
    val posterPath: String?,
    val overview: String,
    val originalTitle: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int
)
