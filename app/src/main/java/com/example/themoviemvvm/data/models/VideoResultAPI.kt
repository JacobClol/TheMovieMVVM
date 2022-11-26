package com.example.themoviemvvm.data.models

import com.example.themoviemvvm.domain.models.Video
import com.google.gson.annotations.SerializedName

data class VideoMovieAPI(
    val id: Long,
    @SerializedName("results")
    val videosList: List<VideoAPI>
){
    fun toListMovie(): List<Video> =
        videosList.map {
            it.toVideo()
        }
}