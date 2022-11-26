package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.Video
import com.example.themoviemvvm.domain.repositories.DetailMovieRepository
import javax.inject.Inject

class GetVideosMovieUseCase @Inject constructor(
    private val detailMovieRepository: DetailMovieRepository
) {
    suspend operator fun invoke(params: DetailMovieParams): List<Video> {
        return detailMovieRepository.getVideoMovieById(params)
    }
}