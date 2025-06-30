package com.sharing.monio.data.api

class PhotoModel : ArrayList<PhotoModelItem>()



typealias PhotoCatagorys = List<PhotoModelItem>


data class PhotoModelItem(
    val category_name: String,
    val images: List<Image>,
)


data class Image(
    val description: String,
    val id: Int,
    val image_url: String,
    val language: String,
    val tags: String,
    val title: String
)