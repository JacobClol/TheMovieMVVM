package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.models.DetailMovieEntity

interface MovieLocalDataSource {
    suspend fun saveFavoriteMovie(movie: DetailMovieEntity)

    suspend fun deleteFavoriteMovie(movie: DetailMovieEntity)

    suspend fun getSaveFavoriteMovie(): List<DetailMovieEntity>
}