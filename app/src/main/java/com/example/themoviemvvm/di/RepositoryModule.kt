package com.example.themoviemvvm.di

import com.example.themoviemvvm.data.repositories.PopularMoviesRepositoryImpl
import com.example.themoviemvvm.domain.repositories.PopularMoviesRepository
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
        popularMoviesRepositoryImpl: PopularMoviesRepositoryImpl
    ): PopularMoviesRepository
}