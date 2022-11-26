package com.example.themoviemvvm.data.repositories

import com.example.themoviemvvm.data.datasources.MoviesRemoteDataSource
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import javax.inject.Inject

class GetMoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : GetMoviesRepository {
    override suspend fun getPopularMovies(params: MovieParams): List<Movie> {
        return moviesRemoteDataSource.getPopularMovies(params).toListMovie()
    }

    override suspend fun getTopRated(params: MovieParams): List<Movie> {
        return moviesRemoteDataSource.getTopRateMovies(params).toListMovie()
    }
}