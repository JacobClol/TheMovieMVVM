package com.example.themoviemvvm.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.core.AbstractViewModel
import com.example.themoviemvvm.core.CoroutinesContextProvider
import com.example.themoviemvvm.core.ErrorProcessor
import com.example.themoviemvvm.domain.models.Movie
import com.example.themoviemvvm.domain.models.MovieParams
import com.example.themoviemvvm.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coroutineContextProvider: CoroutinesContextProvider,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val errorProcessor: ErrorProcessor
) : AbstractViewModel<WorkplaceListScreenState>(WorkplaceListScreenState.Loading){

    fun fechtPopularMovies(){
        viewModelScope.launch {
            mutableState.value = WorkplaceListScreenState.Loading
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

    private fun handleGetMoviesSuccess(list: List<Movie>){
        mutableState.value = WorkplaceListScreenState.Success(list)
    }

    private fun handleGerMoviesError(e: Exception){
       val movieError = errorProcessor.getErrorFromException(e)
       mutableState.value = WorkplaceListScreenState.Failure(movieError)
    }

    fun refresh(){
        fechtPopularMovies()
    }
}