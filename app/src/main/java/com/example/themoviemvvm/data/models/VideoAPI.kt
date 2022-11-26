package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.Video

data class VideoAPI (
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
){
    fun toVideo(): Video{
        return Video(
            key = key,
            name = name,
            site = site,
            type = type
        )
    }
}