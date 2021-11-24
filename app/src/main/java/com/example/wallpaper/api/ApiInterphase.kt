package com.example.wallpaper.api

import com.example.wallpaper.models.SearchModel





import com.example.wallpaper.models.ImageModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiIntephase {

    @Headers("Authorization: Client-ID \$API")
    @GET("/photos")
    fun getImages( //!!!
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Single<List<ImageModel>?>?

    @Headers("Authorization: Client-ID \$API")
    @GET("/search/photos")
    fun searchImages(
        @Query("query") query: String?,
        @Query("per_page") per_page: Int
    ): Single<SearchModel?>?

}