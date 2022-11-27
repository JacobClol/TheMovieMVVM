package com.example.themoviemvvm.di

import com.example.themoviemvvm.data.repositories.DataLocalRepositoryImpl
import com.example.themoviemvvm.data.repositories.DetailMovieRepositoryImpl
import com.example.themoviemvvm.data.repositories.GetMoviesRepositoryImpl
import com.example.themoviemvvm.domain.repositories.DataLocalRepository
import com.example.themoviemvvm.domain.repositories.DetailMovieRepository
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun bindPopularMoviesRepository(
        getMoviesRepositoryImpl: GetMoviesRepositoryImpl
    ): GetMoviesRepository

    @Binds
    @Reusable
    abstract fun bindDetailMovieRepository(
        detailMovieRepositoryImpl: DetailMovieRepositoryImpl
    ) : DetailMovieRepository

    @Binds
    @Reusable
    abstract fun bindDataLocalRepository(
        dataLocalRepositoryImpl: DataLocalRepositoryImpl
    ) : DataLocalRepository
}