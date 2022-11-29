package com.example.themoviemvvm.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Genres

@Entity
data class DetailMovieEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @ColumnInfo(name = "budget")
    val budget: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "trailer")
    val trailer: String?,

    @ColumnInfo(name = "teaser")
    val teaser: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "home_page")
    val homepage: String,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "genres")
    val genres: List<Genres>,

    @ColumnInfo(name = "video")
    val video: Boolean,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
){
    fun toDetailMovie(): DetailMovie{
        return DetailMovie(
            id = id,
            backdropPath = backdropPath,
            budget = budget,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            title = title,
            overview = overview,
            popularity = popularity,
            releaseDate = releaseDate,
            trailer = trailer,
            teaser = teaser,
            voteAverage = voteAverage,
            homepage = homepage,
            voteCount = voteCount,
            posterPath = posterPath,
            genres = genres,
            video = video,
            isFavorite = isFavorite
        )
    }
}
