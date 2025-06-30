package com.sharing.monio.data.api



typealias VideoCatagorys = List<VideoItem>

data class VideoItem(
    val category_name: String,
    val videos: List<VideoX>
)

data class VideoX(
    val description: String,
    val id: Int,
    val language: String,
    val tags: String,
    val title: String,
    val video_url: String
)