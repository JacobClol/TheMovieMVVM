package com.example.themoviemvvm.ui.viewmodel.statesviewmodel

import com.example.themoviemvvm.domain.models.DetailMovie
import com.example.themoviemvvm.domain.models.Video

sealed class MovieScreenState {

    object Loading : MovieScreenState()

    class Success(val movie: DetailMovie) : MovieScreenState()

    class Failure(val error: String) : MovieScreenState()

    class SuccessVideo(val video: List<Video>) : MovieScreenState()

    class SaveMovie(val msg: String) : MovieScreenState()

    object SetVideoLocal : MovieScreenState()

}
