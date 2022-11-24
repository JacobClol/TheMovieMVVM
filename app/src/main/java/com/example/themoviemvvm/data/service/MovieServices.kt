package com.example.themoviemvvm.data.service

import com.example.themoviemvvm.data.models.APIPopularMovieResults
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServices {

    @GET("popular")
    suspend fun getPopularMovies(
       @Query("api_key") apiKey: String,
       @Query("language") language: String?,
       @Query("page") page: Int?,
       @Query("region") region: String?
    ) : APIPopularMovieResults
}