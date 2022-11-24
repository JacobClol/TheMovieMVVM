package com.example.themoviemvvm.data.repositories

import com.example.themoviemvvm.data.datasources.PopularMoviesRemoteDataSource
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.PopularMoviesRepository
import javax.inject.Inject

class PopularMoviesRepositoryImpl @Inject constructor(
    private val popularMoviesRemoteDataSource: PopularMoviesRemoteDataSource
) : PopularMoviesRepository {
    override suspend fun getPopularMovies(params: MovieParams): List<Movie> {
        return popularMoviesRemoteDataSource.getPopularMovies(params).toListMovie()
    }
}