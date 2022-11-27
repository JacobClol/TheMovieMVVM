package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.repositories.DataLocalRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val dataLocalRepository: DataLocalRepository
){
    suspend operator fun invoke(movie: DetailMovie): String{
        return try{
            dataLocalRepository.deleteFavoriteMovie(movie)
            "Delete your favorite movie locally"
        } catch (e: Exception){
            e.message ?: "Can't delete movie locally"
        }
    }
}