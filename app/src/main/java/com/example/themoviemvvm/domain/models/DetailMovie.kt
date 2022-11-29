package com.example.themoviemvvm.domain.models

import android.os.Parcelable
import com.example.themoviemvvm.data.models.DetailMovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    val id: Int,
    val backdropPath: String?,
    val genres: List<Genres>,
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
    val voteCount: Int,
    var trailer: String? = null,
    var teaser: String? = null,
    var isFavorite: Boolean = false
) : Parcelable {
    fun toDetailMovieEntity(): DetailMovieEntity{
        return DetailMovieEntity(
            id = id,
            backdropPath = backdropPath,
            budget = budget,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            title = title,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            trailer = trailer,
            teaser = teaser,
            voteAverage = voteAverage,
            homepage = homepage,
            voteCount = voteCount,
            video = video,
            genres = genres,
            isFavorite = isFavorite
        )
    }

    fun toMovie() : Movie {
        return Movie(
            id = id,
            backdropPath = backdropPath,
            overview = overview,
            originalTitle = originalTitle,
            title = title,
            posterPath = posterPath,
            popularity = popularity,
            voteCount = voteCount,
            voteAverage = voteAverage
        )
    }
}
