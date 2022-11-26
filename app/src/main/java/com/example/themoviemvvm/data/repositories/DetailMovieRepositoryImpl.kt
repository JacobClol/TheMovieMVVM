package com.example.themoviemvvm.data.repositories

import com.example.themoviemvvm.data.datasources.MoviesRemoteDataSource
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.Video
import com.example.themoviemvvm.domain.repositories.DetailMovieRepository
import javax.inject.Inject

class DetailMovieRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : DetailMovieRepository {

    override suspend fun getDetailMovieById(params: DetailMovieParams): DetailMovie {
        return moviesRemoteDataSource.getDetailMovie(params).toDetailMovie()
    }

    override suspend fun getVideoMovieById(params: DetailMovieParams): List<Video> {
        return moviesRemoteDataSource.getVideoMovie(params).toListMovie()
    }
}