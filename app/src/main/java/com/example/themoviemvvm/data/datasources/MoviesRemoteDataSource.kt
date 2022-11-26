package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.models.APIMovieResults
import com.example.themoviemvvm.data.models.DetailMovieAPI
import com.example.themoviemvvm.data.models.VideoMovieAPI
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.MovieParams

interface MoviesRemoteDataSource {

    suspend fun getPopularMovies(params: MovieParams) : APIMovieResults

    suspend fun getTopRateMovies(params: MovieParams) : APIMovieResults

    suspend fun getDetailMovie(params: DetailMovieParams) : DetailMovieAPI

    suspend fun getVideoMovie(params: DetailMovieParams) : VideoMovieAPI
}