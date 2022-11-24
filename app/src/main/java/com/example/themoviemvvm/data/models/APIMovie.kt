package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.Movie
import com.google.gson.annotations.SerializedName

data class APIMovie(
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int

) {
    fun toMovie() : Movie{
        return Movie(
            id = id,
            posterPath = posterPath,
            overview = overview,
            originalTitle = originalTitle,
            title = title,
            backdropPath = backdropPath,
            popularity = popularity,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}