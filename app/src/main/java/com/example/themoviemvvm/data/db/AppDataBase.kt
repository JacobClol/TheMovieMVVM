package com.example.themoviemvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviemvvm.core.utils.Converters
import com.example.themoviemvvm.data.models.DetailMovieEntity

@Database(entities = [DetailMovieEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}