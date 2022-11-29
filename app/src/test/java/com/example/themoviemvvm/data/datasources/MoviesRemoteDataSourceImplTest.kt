package com.example.themoviemvvm.data.datasources

import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.data.models.APIMovie
import com.example.themoviemvvm.data.models.APIMovieResults
import com.example.themoviemvvm.data.service.MovieServices
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


internal class MoviesRemoteDataSourceImplTest {

    private val movieServices = mockk<MovieServices>()
    private lateinit var moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl

    private val mockAPIMovieResults = APIMovieResults(
        moviesList = listOf(
            APIMovie(
                id = 1,
                posterPath = "http://poster.test/",
                overview = "Overview test movie",
                originalTitle = "Original title movie",
                title = "Title movie",
                backdropPath = "http://backdrop.test/",
                popularity = 122.9,
                voteAverage = 8.5,
                voteCount = 10
            ),
            APIMovie(
                id = 2,
                posterPath = "http://poster2.test/",
                overview = "Overview test movie 2",
                originalTitle = "Original test movie 2",
                title = "Title movie 2",
                backdropPath = "http://backdrop2.test/",
                popularity = 102.9,
                voteAverage = 7.5,
                voteCount = 40
            )
        )
    )

    private val mockMovieParams = MovieParams(
        apiKey = BuildConfig.MOVIE_API_KEY
    )

    @Before
    fun setup() {
        moviesRemoteDataSourceImpl = MoviesRemoteDataSourceImpl(
            movieServices
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getPopularMovies is called then should run the getPopularMovies from service and return a APIMovieResult list`() = runTest {
        coEvery {
            movieServices.getPopularMovies(
                apiKey = mockMovieParams.apiKey,
                language = mockMovieParams.language,
                page = mockMovieParams.page,
                region = mockMovieParams.region
            )
        } answers {
            mockAPIMovieResults
        }

        val result = moviesRemoteDataSourceImpl.getPopularMovies(mockMovieParams)

        Assert.assertEquals(mockAPIMovieResults, result)

        coVerify(exactly = 1) {
            moviesRemoteDataSourceImpl.getPopularMovies(mockMovieParams)
        }

        coVerify(exactly = 0) {
            moviesRemoteDataSourceImpl.getTopRateMovies(mockMovieParams)
        }
    }
}