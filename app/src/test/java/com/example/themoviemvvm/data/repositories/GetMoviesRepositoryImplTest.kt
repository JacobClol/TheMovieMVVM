package com.example.themoviemvvm.data.repositories

import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.data.datasources.MoviesRemoteDataSource
import com.example.themoviemvvm.data.models.APIMovie
import com.example.themoviemvvm.data.models.APIMovieResults
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import com.google.gson.annotations.SerializedName
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMoviesRepositoryImplTest {

    private val moviesRemoteDataSource = mockk<MoviesRemoteDataSource>()
    private lateinit var getMoviesRepository: GetMoviesRepository

    private val mockMovieList = listOf(
        Movie(
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
        Movie(
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
        getMoviesRepository = GetMoviesRepositoryImpl(
            moviesRemoteDataSource
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getPopularMovies is called then should run the getPopularMovies from datasource and return a movie list`() = runTest {
        coEvery {
            moviesRemoteDataSource.getPopularMovies(mockMovieParams)
        } answers {
            mockAPIMovieResults
        }

        val result = getMoviesRepository.getPopularMovies(mockMovieParams)

        Assert.assertEquals(mockMovieList, result)

        coVerify(exactly = 1) {
            moviesRemoteDataSource.getPopularMovies(mockMovieParams)
        }

        coVerify(exactly = 0) {
            moviesRemoteDataSource.getTopRateMovies(mockMovieParams)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when getTopRated is called then should run the getTopRated from datasource and return a movie list`() = runTest {
        coEvery {
            moviesRemoteDataSource.getTopRateMovies(mockMovieParams)
        } answers {
            mockAPIMovieResults
        }

        val result = getMoviesRepository.getTopRated(mockMovieParams)

        Assert.assertEquals(mockMovieList, result)

        coVerify(exactly = 1) {
            moviesRemoteDataSource.getTopRateMovies(mockMovieParams)
        }

        coVerify(exactly = 0) {
            moviesRemoteDataSource.getPopularMovies(mockMovieParams)
        }
    }

}