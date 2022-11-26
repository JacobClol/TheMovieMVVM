package com.example.themoviemvvm.di

import com.example.themoviemvvm.data.datasources.MoviesRemoteDataSource
import com.example.themoviemvvm.data.datasources.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun bindPopularMoviesRemoteDataSource(
        popularMoviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl
    ): MoviesRemoteDataSource
}