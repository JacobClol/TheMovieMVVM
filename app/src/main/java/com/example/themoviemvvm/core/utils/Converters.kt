package com.example.themoviemvvm.core.utils

import androidx.room.TypeConverter
import com.example.themoviemvvm.core.fromJson
import com.example.themoviemvvm.domain.models.Genres
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromString(value: String): List<Genres> {
        return try {
            Gson().fromJson(value)
        } catch (e: Exception){
            arrayListOf()
        }
    }

    @TypeConverter
    fun fromArrayList(list: List<Genres>): String {
        return Gson().toJson(list)
    }
}