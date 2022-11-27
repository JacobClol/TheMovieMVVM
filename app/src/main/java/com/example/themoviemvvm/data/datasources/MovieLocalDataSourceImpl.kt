package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.db.MovieDao
import com.example.themoviemvvm.data.models.DetailMovieEntity
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun saveFavoriteMovie(movie: DetailMovieEntity) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: DetailMovieEntity) {
       movieDao.deleteMovie(movie)
    }

    override suspend fun getSaveFavoriteMovie(): List<DetailMovieEntity> {
        return movieDao.getSavedMovies()
    }
}