package com.example.themoviemvvm.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviemvvm.data.models.DetailMovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM DetailMovieEntity")
    suspend fun getSavedMovies() : List<DetailMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: DetailMovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: DetailMovieEntity)

}