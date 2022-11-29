package com.example.themoviemvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Genres
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.usecases.GetLocalFavotiresMovieUseCase
import com.example.themoviemvvm.domain.usecases.GetPopularMoviesUseCase
import com.example.themoviemvvm.domain.usecases.GetTopRateMoviesUseCase
import com.example.themoviemvvm.ui.viewmodel.MainViewModel
import com.example.themoviemvvm.ui.viewmodel.statesviewmodel.MovieListScreenState
import com.example.themoviemvvm.utils.CoroutineTestRule
import com.example.themoviemvvm.utils.relaxedMockk
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.verify
import java.net.UnknownHostException
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instanExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineRule = CoroutineTestRule()
    private val getPopularMoviesUseCase = relaxedMockk<GetPopularMoviesUseCase>()
    private val getTopRateMoviesUsecase = relaxedMockk<GetTopRateMoviesUseCase>()
    private val getLocalFavotiresMovieUseCase = relaxedMockk<GetLocalFavotiresMovieUseCase>()
    private val observer = relaxedMockk<Observer<MovieListScreenState>>()
    private lateinit var mainViewModel: MainViewModel

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

    private val mockDetailMovieList = listOf(
        DetailMovie(
            id = 1,
            backdropPath = "http://backdrop.test/",
            genres = listOf(
                Genres(
                    id = 1,
                    name = "Action"
                )
            ),
            budget = 1000,
            originalLanguage = "Original language movie",
            originalTitle = "Original title movie",
            title = "Title movie",
            overview = "Overview test movie",
            popularity = 122.9,
            posterPath = "http://poster.test/",
            releaseDate = "28/11/2022",
            video = true,
            voteAverage = 8.5,
            homepage = "http://homepage.movie",
            voteCount = 10,
            trailer = "http://triler.movie",
            teaser = null,
            isFavorite = false
        )
    )

    private val mockMovieParams = MovieParams(
        apiKey = BuildConfig.MOVIE_API_KEY
    )

    @Before
    fun setup() {
        mainViewModel = MainViewModel(
            coroutineRule.coroutineContextProvider,
            getPopularMoviesUseCase,
            getTopRateMoviesUsecase,
            getLocalFavotiresMovieUseCase
        )
    }

    @Test
    fun `when MainViewModel is created then should get a popular movie list and cath Success state`() {
        val slots = mutableListOf<MovieListScreenState>()
        coEvery {
            getPopularMoviesUseCase(mockMovieParams)
        } answers {
            mockMovieList
        }

        mainViewModel.fetchPopularMovies()
        mainViewModel.state.asLiveData().observeForever(observer)

        verify {
            observer.onChanged(capture(slots))
        }

        Assert.assertTrue(slots.last() is MovieListScreenState.Success)

        coVerify(exactly = 1) {
            getPopularMoviesUseCase.invoke(mockMovieParams)
        }

        coVerify(exactly = 0) {
            getTopRateMoviesUsecase.invoke(mockMovieParams)
            getLocalFavotiresMovieUseCase.invoke()
        }
    }

    @Test
    fun `when fetchTopRateMovies is called then should get Top Rate movie list and catch Success state`() {
        val slots = mutableListOf<MovieListScreenState>()
        coEvery {
            getTopRateMoviesUsecase(mockMovieParams)
        } answers {
            mockMovieList
        }

        mainViewModel.fetchTopRateMovies()
        mainViewModel.state.asLiveData().observeForever(observer)

        verify {
            observer.onChanged(capture(slots))
        }

        Assert.assertTrue(slots.last() is MovieListScreenState.Success)

        coVerify(exactly = 1) {
            getTopRateMoviesUsecase.invoke(mockMovieParams)
        }

        coVerify(exactly = 0) {
            getPopularMoviesUseCase.invoke(mockMovieParams)
            getLocalFavotiresMovieUseCase.invoke()
        }
    }

    @Test
    fun `when fetchFavoriteMovies is called then should get favorite local movie list and catch SuccesFavorite state`() {
        val slots = mutableListOf<MovieListScreenState>()
        coEvery {
            getLocalFavotiresMovieUseCase()
        } answers {
            mockDetailMovieList
        }

        mainViewModel.fetchFavoriteMovies()
        mainViewModel.state.asLiveData().observeForever(observer)

        verify {
            observer.onChanged(capture(slots))
        }

        Assert.assertTrue(slots.last() is MovieListScreenState.SuccessFavorite)

        coVerify(exactly = 1) {
            getLocalFavotiresMovieUseCase.invoke()
        }

        coVerify(exactly = 0) {
            getPopularMoviesUseCase.invoke(mockMovieParams)
            getTopRateMoviesUsecase.invoke(mockMovieParams)
        }
    }

    @Test
    fun `given a error when get some movie list then should catch a Failure state`() {
        val slots = mutableListOf<MovieListScreenState>()
        coEvery {
            getPopularMoviesUseCase(mockMovieParams)
        } answers {
            throw UnknownHostException()
        }

        mainViewModel.fetchPopularMovies()
        mainViewModel.state.asLiveData().observeForever(observer)

        verify {
            observer.onChanged(capture(slots))
        }

        Assert.assertTrue(slots.last() is MovieListScreenState.Failure)

        coVerify(exactly = 1) {
            getPopularMoviesUseCase.invoke(mockMovieParams)
        }

        coVerify(exactly = 0) {
            getTopRateMoviesUsecase.invoke(mockMovieParams)
            getLocalFavotiresMovieUseCase.invoke()
        }
    }

    @Test
    fun `when refresh is called then should return a list movie`() {
        val slots = mutableListOf<MovieListScreenState>()
        coEvery {
            getPopularMoviesUseCase(mockMovieParams)
        } answers {
            mockMovieList
        }

        mainViewModel.refresh()
        mainViewModel.state.asLiveData().observeForever(observer)

        verify {
            observer.onChanged(capture(slots))
        }

        with((slots.last() as MovieListScreenState.Success).data.first()) {
            Assert.assertEquals(id, 1)
            Assert.assertEquals(posterPath, "http://poster.test/")
            Assert.assertEquals(overview, "Overview test movie")
            Assert.assertEquals(originalTitle, "Original title movie")
            Assert.assertEquals(title, "Title movie")
            Assert.assertEquals(backdropPath, "http://backdrop.test/")
            Assert.assertEquals(popularity, 122.9, 0.0)
            Assert.assertEquals(voteAverage, 8.5, 0.0)
            Assert.assertEquals(voteCount, 10)
        }

    }
}