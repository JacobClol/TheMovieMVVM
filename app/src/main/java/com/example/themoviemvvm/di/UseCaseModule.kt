package com.example.themoviemvvm.di

import com.example.themoviemvvm.domain.repositories.DetailMovieRepository
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import com.example.themoviemvvm.domain.usecases.GetDetailMovieUseCase
import com.example.themoviemvvm.domain.usecases.GetPopularMoviesUseCase
import com.example.themoviemvvm.domain.usecases.GetTopRateMoviesUseCase
import com.example.themoviemvvm.domain.usecases.GetVideosMovieUseCase
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
        getMoviesRepository: GetMoviesRepository
    ) = GetPopularMoviesUseCase(getMoviesRepository)

    @Provides
    @Reusable
    fun provideGetTopRateMoviesUseCase(
        getMoviesRepository: GetMoviesRepository
    ) = GetTopRateMoviesUseCase(getMoviesRepository)

    @Provides
    @Reusable
    fun provideGetDetailMovieUseCase(
        detailMovieRepository: DetailMovieRepository
    ) = GetDetailMovieUseCase(detailMovieRepository)

    @Provides
    @Reusable
    fun provideGetVideosMovieUseCase(
        detailMovieRepository: DetailMovieRepository
    ) = GetVideosMovieUseCase(detailMovieRepository)
}