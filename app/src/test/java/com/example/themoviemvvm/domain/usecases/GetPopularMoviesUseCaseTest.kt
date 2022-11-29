package com.example.themoviemvvm.domain.usecases

import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.repositories.GetMoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    private val getMoviesRepository = mockk<GetMoviesRepository>()
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

    private val mockMovieParams = MovieParams(
        apiKey = BuildConfig.MOVIE_API_KEY
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when GetPopularMoviesUseCaseTest is invoke then shoul return movie list`() = runTest {
        coEvery {
            getMoviesRepository.getPopularMovies(mockMovieParams)
        } answers {
            mockMovieList
        }

        val result = GetPopularMoviesUseCase(getMoviesRepository).invoke(mockMovieParams)

        Assert.assertEquals(mockMovieList, result)

        coVerify(exactly = 1) {
            getMoviesRepository.getPopularMovies(mockMovieParams)
        }
    }
}