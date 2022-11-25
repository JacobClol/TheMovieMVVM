package com.example.themoviemvvm.ui.viewmodel

import com.example.themoviemvvm.domain.models.Movie

sealed class MovieListScreenState {

    object Loading : MovieListScreenState()

    class Success(val data:List<Movie>): MovieListScreenState()

    class Failure(val error: String): MovieListScreenState()

}
