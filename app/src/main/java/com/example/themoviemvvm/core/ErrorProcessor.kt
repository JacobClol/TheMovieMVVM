package com.example.themoviemvvm.core

interface ErrorProcessor {
    fun getErrorFromException(error: Throwable): Error
}