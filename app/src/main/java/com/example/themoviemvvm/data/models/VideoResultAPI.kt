package com.example.themoviemvvm.data.models

import com.google.gson.annotations.SerializedName

data class VideoMovieAPI(
    val id: Long,
    @SerializedName("results")
    val videosList: List<VideoAPI>
)