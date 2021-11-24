package com.example.wallpaper

import com.nostra13.universalimageloader.core.ImageLoader

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import com.nostra13.universalimageloader.core.DisplayImageOptions

import android.app.Application


class App : Application() {
     var unsplashService: UnsplashService? = null


    override fun onCreate() {
        super.onCreate()
        unsplashService = UnsplashService()
        val defaultOptions = DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .build()
        val config = ImageLoaderConfiguration.Builder(this)
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(LruMemoryCache(20 * 1024 * 1024))
            .memoryCacheSize(20 * 1024 * 1024)
            .build()
        ImageLoader.getInstance().init(config)
    }



}