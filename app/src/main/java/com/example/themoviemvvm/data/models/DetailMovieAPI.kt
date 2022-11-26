package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.DetailMovie
import com.google.gson.annotations.SerializedName

data class DetailMovieAPI(
    val id: Int,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    val genres: List<GenresAPI>,

    val budget: Int,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val title: String,

    val overview: String,

    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val homepage: String,

    @SerializedName( "vote_count")
    val voteCount: Int
) {
    fun toDetailMovie() : DetailMovie{
        return DetailMovie(
            id = id,
            backdropPath = backdropPath,
            genres = genres.map { it.toGenres() },
            budget = budget,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            title = title,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            video = video,
            voteAverage = voteAverage,
            homepage = homepage,
            voteCount = voteCount
        )
    }
}

