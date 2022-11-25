package com.example.themoviemvvm.di

import com.example.themoviemvvm.core.CoroutinesContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContextProvider(): CoroutinesContextProvider {
        return if (isRunningTest?.get() == true){
            object : CoroutinesContextProvider {
                override val io: CoroutineContext
                    get() = Dispatchers.Unconfined
                override val main: CoroutineContext
                    get() = Dispatchers.Unconfined
                override val computation: CoroutineContext
                    get() = Dispatchers.Unconfined
                override val immediate: CoroutineContext
                    get() = Dispatchers.Unconfined
            }
        } else {
            object : CoroutinesContextProvider {
                override val io: CoroutineContext
                    get() = Dispatchers.IO
                override val main: CoroutineContext
                    get() = Dispatchers.Main
                override val computation: CoroutineContext
                    get() = Dispatchers.Default
                override val immediate: CoroutineContext
                    get() = Dispatchers.Main.immediate
            }
        }
    }

    var isRunningTest: AtomicBoolean? = null
}