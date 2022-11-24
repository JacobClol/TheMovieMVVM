package com.example.themoviemvvm.di

import com.example.themoviemvvm.data.datasources.PopularMoviesRemoteDataSource
import com.example.themoviemvvm.data.datasources.PopularMoviesRemoteDataSourceImpl
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
        popularMoviesRemoteDataSourceImpl: PopularMoviesRemoteDataSourceImpl
    ): PopularMoviesRemoteDataSource
}