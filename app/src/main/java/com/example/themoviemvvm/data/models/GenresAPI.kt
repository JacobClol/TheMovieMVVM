package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.Genres

data class GenresAPI(
    val id: Int,
    val name: String
){
    fun toGenres(): Genres{
        return Genres(
            id = id,
            name = name
        )
    }
}