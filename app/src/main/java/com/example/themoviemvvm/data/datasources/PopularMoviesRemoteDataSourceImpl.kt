package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.data.models.APIPopularMovieResults
import com.example.themoviemvvm.data.service.MovieServices
import com.example.themoviemvvm.domain.models.MovieParams
import javax.inject.Inject

class PopularMoviesRemoteDataSourceImpl @Inject constructor(
    private val movieServices: MovieServices
) : PopularMoviesRemoteDataSource {

    override suspend fun getPopularMovies(param: MovieParams): APIPopularMovieResults {
        return movieServices.getPopularMovies(
            param.apiKey,
            param.language,
            param.page,
            param.region
        )
    }
}