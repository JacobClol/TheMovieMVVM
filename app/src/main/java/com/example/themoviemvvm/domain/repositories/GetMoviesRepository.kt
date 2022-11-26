package com.example.themoviemvvm.domain.repositories

import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams

interface GetMoviesRepository {
    suspend fun getPopularMovies(params: MovieParams) : List<Movie>

    suspend fun getTopRated(params: MovieParams) : List<Movie>
}