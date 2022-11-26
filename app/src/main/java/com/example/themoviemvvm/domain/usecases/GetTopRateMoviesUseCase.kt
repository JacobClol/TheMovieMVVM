package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import javax.inject.Inject

class GetTopRateMoviesUseCase @Inject constructor(
    private val getMoviesRepository: GetMoviesRepository
) {
    suspend operator fun invoke(params: MovieParams): List<Movie> {
        return getMoviesRepository.getTopRated(params)
    }
}