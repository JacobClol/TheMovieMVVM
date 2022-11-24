package com.example.themoviemvvm.core

import kotlin.coroutines.CoroutineContext

interface CoroutinesContextProvider {
    val io: CoroutineContext
    val main: CoroutineContext
    val computation: CoroutineContext
    val immediate: CoroutineContext
}