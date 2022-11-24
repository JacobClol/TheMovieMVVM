package com.example.themoviemvvm.di

import com.example.themoviemvvm.domain.repositories.PopularMoviesRepository
import com.example.themoviemvvm.domain.usecases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Reusable
    fun provideGetPopularMoviesUseCase(
        popularMoviesRepository: PopularMoviesRepository
    ) = GetPopularMoviesUseCase(popularMoviesRepository)
}