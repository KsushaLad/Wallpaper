package com.example.wallpaper

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.example.wallpaper.api.ApiIntephase
import okhttp3.Request






class UnsplashService {

    companion object{
        var KEY_API = "e8LZ4f3PcORqogORrr92K_Uz3HzG8xx48IXkOonfDsw"
    }

    init {
        val retrofit: Retrofit = createRetrofit()
        retrofit.create(ApiIntephase::class.java).also { apiIntephase = it }
    }

    var apiIntephase: ApiIntephase? = null

    fun UnsplashService() {
        val retrofit = createRetrofit()
        apiIntephase = retrofit.create(ApiIntephase::class.java)
    }


    @JvmName("getApiIntephase1")
    fun getApiIntephase(): ApiIntephase? {
        return apiIntephase
    }



    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("client_id", KEY_API)
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }
}