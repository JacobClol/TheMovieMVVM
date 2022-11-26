package com.example.themoviemvvm.domain.repositories

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.Video

interface DetailMovieRepository {
    suspend fun getDetailMovieById(params: DetailMovieParams): DetailMovie

    suspend fun getVideoMovieById(params: DetailMovieParams) : List<Video>
}