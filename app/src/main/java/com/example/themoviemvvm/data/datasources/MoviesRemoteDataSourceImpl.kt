package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.models.APIMovieResults
import com.example.themoviemvvm.data.models.DetailMovieAPI
import com.example.themoviemvvm.data.models.VideoMovieAPI
import com.example.themoviemvvm.data.service.MovieServices
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.MovieParams
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val movieServices: MovieServices
) : MoviesRemoteDataSource {

    override suspend fun getPopularMovies(params: MovieParams): APIMovieResults {
        return movieServices.getPopularMovies(
            params.apiKey,
            params.language,
            params.page,
            params.region
        )
    }

    override suspend fun getTopRateMovies(params: MovieParams): APIMovieResults {
        return movieServices.getTopRateMovies(
            params.apiKey,
            params.language,
            params.page,
            params.region
        )
    }

    override suspend fun getDetailMovie(params: DetailMovieParams): DetailMovieAPI {
        return movieServices.getDetailMovie(
            params.idMovie,
            params.apiKey,
            params.language
        )
    }

    override suspend fun getVideoMovie(params: DetailMovieParams): VideoMovieAPI {
        return movieServices.getVideoMovie(
            params.idMovie,
            params.apiKey,
            params.language
        )
    }
}