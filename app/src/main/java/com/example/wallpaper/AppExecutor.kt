package com.example.wallpaper

import android.os.Handler
import android.os.Looper


import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AppExecutor(val executor: Executor,val  service: ExecutorService) {

    class MainThreadHandler : Executor {
        //перенос из второстепенного потока на основной
        private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
             //взятие главного потока
        override fun execute(command: Runnable?) {
            command?.let { mainHandler.post(it) } //возможность запуска
        }
    }

    fun getMainIO() = executor
    fun getSubIO() = service

    companion object {
        private var instance: AppExecutor? = null

        fun getInstances(): AppExecutor { //для создания только второго потока
            return instance?.let {
                it
            } ?: kotlin.run {
                val executor = AppExecutor(
                    MainThreadHandler(),
                    Executors.newSingleThreadExecutor()
                )
                instance = executor
                executor
            }
        }
    }
}