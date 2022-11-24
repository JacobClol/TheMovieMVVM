package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.Movie
import com.google.gson.annotations.SerializedName

data class APIPopularMovieResults(
    @SerializedName("results")
    val moviesList : List<APIMovie>
) {
    fun toListMovie() : List<Movie> =
        moviesList.map {
            it.toMovie()
        }
}