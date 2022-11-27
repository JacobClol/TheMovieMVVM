package com.example.themoviemvvm.data.repositories

import com.example.themoviemvvm.data.datasources.MovieLocalDataSource
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.repositories.DataLocalRepository
import javax.inject.Inject

class DataLocalRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource
) : DataLocalRepository {
    override suspend fun saveFavoriteMovie(movie: DetailMovie) {
        movieLocalDataSource.saveFavoriteMovie(movie.toDetailMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(movie: DetailMovie) {
        movieLocalDataSource.deleteFavoriteMovie(movie.toDetailMovieEntity())
    }

    override suspend fun getLocalFavoriteMovies(): List<DetailMovie> {
        return movieLocalDataSource.getSaveFavoriteMovie().map {
            it.toDetailMovie()
        }
    }
}