package com.example.wallpaper.api

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit




class ApiUtilities {
    private var retrofit: Retrofit? = null
    val BASE_URL = "https://api.unsplash.com/"
    val API = "e8LZ4f3PcORqogORrr92K_Uz3HzG8xx48IXkOonfDsw"

    fun getApiInterphase(): ApiIntephase? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory
                        .create()
                )
                .build()
        }
        return retrofit!!.create(ApiIntephase::class.java)
    }
}