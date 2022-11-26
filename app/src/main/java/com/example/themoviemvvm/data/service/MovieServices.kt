package com.example.themoviemvvm.data.service

import com.example.themoviemvvm.data.models.APIMovieResults
import com.example.themoviemvvm.data.models.DetailMovieAPI
import com.example.themoviemvvm.data.models.VideoMovieAPI
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET("popular")
    suspend fun getPopularMovies(
       @Query("api_key") apiKey: String,
       @Query("language") language: String?,
       @Query("page") page: Int?,
       @Query("region") region: String?
    ) : APIMovieResults

    @GET("top_rated")
    suspend fun getTopRateMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ) : APIMovieResults

    @GET("{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") idMovie: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
    ) : DetailMovieAPI

    @GET("{idMovie}/videos")
    suspend fun getVideoMovie(
        @Path("idMovie") idMovie: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): VideoMovieAPI

}