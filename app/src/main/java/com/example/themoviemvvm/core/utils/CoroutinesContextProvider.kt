package com.example.themoviemvvm.core.utils

import kotlin.coroutines.CoroutineContext

interface CoroutinesContextProvider {
    val io: CoroutineContext
    val main: CoroutineContext
    val computation: CoroutineContext
    val immediate: CoroutineContext
}