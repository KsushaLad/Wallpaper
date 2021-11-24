package com.example.wallpaper


import com.example.wallpaper.models.ImageModel


interface OnDataReceived {
    fun onReceived(list: List<ImageModel?>?)
}