package com.example.themoviemvvm.ui.viewmodel.statesviewmodel

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Movie

sealed class MovieListScreenState {

    object Loading : MovieListScreenState()

    class Success(val data: List<Movie>) : MovieListScreenState()

    class SuccessFavorite(val data: List<DetailMovie>) : MovieListScreenState()

    class Failure(val error: String) : MovieListScreenState()

}
