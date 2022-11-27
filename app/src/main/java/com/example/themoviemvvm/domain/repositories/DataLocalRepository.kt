package com.example.themoviemvvm.domain.repositories

import com.example.themoviemvvm.domain.models.DetailMovie

interface DataLocalRepository {
    suspend fun saveFavoriteMovie(movie: DetailMovie)

    suspend fun deleteFavoriteMovie(movie: DetailMovie)

    suspend fun getLocalFavoriteMovies() : List<DetailMovie>
}