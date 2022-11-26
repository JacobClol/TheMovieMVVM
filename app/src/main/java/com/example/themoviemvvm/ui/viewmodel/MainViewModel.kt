package com.example.themoviemvvm.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.core.AbstractViewModel
import com.example.themoviemvvm.core.CoroutinesContextProvider
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.usecases.GetPopularMoviesUseCase
import com.example.themoviemvvm.domain.usecases.GetTopRateMoviesUseCase
import com.example.themoviemvvm.ui.viewmodel.statesviewmodel.MovieListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coroutineContextProvider: CoroutinesContextProvider,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRateMoviesUsecase: GetTopRateMoviesUseCase
) : AbstractViewModel<MovieListScreenState>(MovieListScreenState.Loading){

    fun fetchPopularMovies(){
        viewModelScope.launch {
            mutableState.value = MovieListScreenState.Loading
            try {
                val popularMovies = withContext(coroutineContextProvider.io){
                    getPopularMoviesUseCase(MovieParams(
                        apiKey = BuildConfig.MOVIE_API_KEY
                    ))
                }
                handleGetMoviesSuccess(popularMovies)
            } catch (e: Exception){
                handleGerMoviesError(e)
            }
        }
    }

    fun fetchTopRateMovies(){
        viewModelScope.launch {
            mutableState.value = MovieListScreenState.Loading
            try {
                val topRateMovies = withContext(coroutineContextProvider.io){
                    getTopRateMoviesUsecase.invoke(
                        MovieParams(
                        apiKey = BuildConfig.MOVIE_API_KEY
                    )
                    )
                }
                handleGetMoviesSuccess(topRateMovies)
            } catch (e: Exception){
                handleGerMoviesError(e)
            }
        }
    }

    private fun handleGetMoviesSuccess(list: List<Movie>){
        mutableState.value = MovieListScreenState.Success(list)
    }

    private fun handleGerMoviesError(e: Exception){
       val error = e.message ?: "Can't get the data"
        mutableState.value = MovieListScreenState.Failure(error)
    }

    fun refresh(){
        fetchPopularMovies()
    }
}