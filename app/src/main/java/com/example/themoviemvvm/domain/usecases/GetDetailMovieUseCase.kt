package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.repositories.DetailMovieRepository
import javax.inject.Inject

class GetDetailMovieUseCase @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) {
    suspend operator fun invoke(params: DetailMovieParams): DetailMovie {
        return detailMovieRepository.getDetailMovieById(params)
    }
}