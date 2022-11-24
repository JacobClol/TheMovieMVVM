package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.models.APIPopularMovieResults
import com.example.themoviemvvm.domain.models.MovieParams

interface PopularMoviesRemoteDataSource {

    suspend fun getPopularMovies(param: MovieParams) : APIPopularMovieResults
}