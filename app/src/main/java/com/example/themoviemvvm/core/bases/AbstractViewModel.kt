package com.example.themoviemvvm.core.bases

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class AbstractViewModel<ViewState>(initialState: ViewState) : ViewModel() {

    protected val mutableState = MutableStateFlow(initialState)
    val state: Flow<ViewState> get() = mutableState
}