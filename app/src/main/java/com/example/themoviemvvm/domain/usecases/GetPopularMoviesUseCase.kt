package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.PopularMoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) {
    suspend operator fun invoke(params: MovieParams) : List<Movie>{
        return popularMoviesRepository.getPopularMovies(params)
    }
}