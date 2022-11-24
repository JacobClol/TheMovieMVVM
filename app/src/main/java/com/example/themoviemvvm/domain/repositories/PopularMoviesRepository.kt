package com.example.themoviemvvm.domain.repositories

import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams

interface PopularMoviesRepository {
    suspend fun getPopularMovies(params: MovieParams) : List<Movie>
}