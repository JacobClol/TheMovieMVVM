package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.repositories.DataLocalRepository
import javax.inject.Inject

class GetLocalFavotiresMovieUseCase @Inject constructor(
    private val dataLocalRepository: DataLocalRepository
) {
    suspend operator fun invoke(): List<DetailMovie> {
        return dataLocalRepository.getLocalFavoriteMovies()
    }
}