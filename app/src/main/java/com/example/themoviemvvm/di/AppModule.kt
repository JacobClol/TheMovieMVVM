package com.example.themoviemvvm.di

import android.content.Context
import androidx.room.Room
import com.example.themoviemvvm.core.utils.CoroutinesContextProvider
import com.example.themoviemvvm.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        MOVIE_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(dataBase: AppDataBase) = dataBase.movieDao()

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

private const val MOVIE_DATABASE_NAME = "movie_table"