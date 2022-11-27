package com.example.themoviemvvm.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.themoviemvvm.BuildConfig
import com.example.themoviemvvm.core.bases.AbstractViewModel
import com.example.themoviemvvm.core.utils.CoroutinesContextProvider
import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.DetailMovieParams
import com.example.themoviemvvm.domain.models.Video
import com.example.themoviemvvm.domain.usecases.DeleteFavoriteMovieUseCase
import com.example.themoviemvvm.domain.usecases.GetDetailMovieUseCase
import com.example.themoviemvvm.domain.usecases.GetVideosMovieUseCase
import com.example.themoviemvvm.domain.usecases.SaveFavoriteMovieUseCase
import com.example.themoviemvvm.ui.viewmodel.statesviewmodel.MovieScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val courutineContextProvider: CoroutinesContextProvider,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getVideoMovieUseCase: GetVideosMovieUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
) : AbstractViewModel<MovieScreenState>(MovieScreenState.Loading){
    
    fun fetchDetailMovie(idMovie: Int){
        viewModelScope.launch { 
            mutableState.value = MovieScreenState.Loading
            try {
                val detailMovie = withContext(courutineContextProvider.io){
                    getDetailMovieUseCase(DetailMovieParams(
                        idMovie = idMovie,
                        apiKey = BuildConfig.MOVIE_API_KEY
                    ))
                }
                handleGetMoviesSuccess(detailMovie)
            } catch (e: Exception){
                handleMoviesError(e)
            }
        }
    }

    fun fetchVideoMovie(idMovie: Int){
        viewModelScope.launch {
            mutableState.value = MovieScreenState.Loading
            try{
                val videoMovie = withContext(courutineContextProvider.io){
                    getVideoMovieUseCase(DetailMovieParams(
                        idMovie = idMovie,
                        apiKey = BuildConfig.MOVIE_API_KEY
                    ))
                }
                handleGetVideosMovieSuccess(videoMovie)
            } catch (e: Exception){
                handleMoviesError(e)
            }
        }
    }

    fun saveOrDeleteFavoriteMovie(save: Boolean, movie: DetailMovie){
        viewModelScope.launch {
            mutableState.value = MovieScreenState.Loading
            try {
               val result = withContext(courutineContextProvider.io){
                    if (save){
                        deleteFavoriteMovieUseCase(movie)
                    } else {
                        saveFavoriteMovieUseCase(movie)
                    }
                }
                handleSaveMovie(result)
            } catch (e: Exception){
                handleMoviesError(e)
            }
        }
    }

    private fun handleGetMoviesSuccess(movie: DetailMovie){
        mutableState.value = MovieScreenState.Success(movie)
    }

    private fun handleGetVideosMovieSuccess(videos: List<Video>){
        mutableState.value = MovieScreenState.SuccessVideo(videos)
    }

    private fun handleMoviesError(e: Exception){
        val error = e.message ?: "Can't get the data try again"
        mutableState.value = MovieScreenState.Failure(error)
    }

    private fun handleSaveMovie(msg: String){
        mutableState.value = MovieScreenState.SaveMovie(msg)
    }
}