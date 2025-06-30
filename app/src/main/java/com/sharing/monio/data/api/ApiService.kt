package com.sharing.monio.data.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("signup.php")
    suspend fun insertUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ApiResponse>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ApiResponse>

    @FormUrlEncoded
    @POST("download_count.php")
    suspend fun count(
        @Field("count") count: Int,

    ): Response<ApiResponse>

    @GET("fetch_photo.php")
    suspend fun getPhotoFetch() : PhotoCatagorys

    @GET("fetch_video.php")
    suspend fun getVideoFetch() : VideoCatagorys

    @GET("all_photo.php")
    suspend fun getAllPhotoFetch() : List<Image>

    @GET("all_video_fetch.php")
    suspend fun getAllVideoFetch() : List<VideoX>

}