package com.example.themoviemvvm.domain.models

import com.example.themoviemvvm.data.models.GenresAPI

data class DetailMovie(
    val id: Int,
    val backdropPath: String?,
    val genres: List<GenresAPI>,
    val budget: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val homepage: String,
    val voteCount: Int
)
