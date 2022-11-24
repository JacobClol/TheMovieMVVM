package com.example.themoviemvvm.ui.viewmodel

import com.example.themoviemvvm.core.Error
import com.example.themoviemvvm.domain.models.Movie

sealed class WorkplaceListScreenState {

    object Loading : WorkplaceListScreenState()

    class Success(val data:List<Movie>): WorkplaceListScreenState()

    class Failure(val error: Error): WorkplaceListScreenState()

}
