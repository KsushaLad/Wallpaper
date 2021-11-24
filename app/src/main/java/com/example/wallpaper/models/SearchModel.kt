package com.example.wallpaper.models

//class SearchModel {
//
//    private var results: ArrayList<ImageModel>? = null
//
//    fun SearchModel(results: ArrayList<ImageModel>?) {
//        this.results = results
//    }
//
//    fun getResults(): ArrayList<ImageModel>? {
//        return results
//    }
//
//    fun setResults(results: ArrayList<ImageModel>?) {
//        this.results = results
//    }
//
//}

data class SearchModel(
    val results: ArrayList<ImageModel>? = null
)