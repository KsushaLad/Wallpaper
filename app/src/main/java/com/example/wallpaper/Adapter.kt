package com.example.wallpaper


import android.annotation.SuppressLint
import android.content.Context

import androidx.recyclerview.widget.RecyclerView

import com.example.wallpaper.models.ImageModel

import android.content.Intent

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import com.bumptech.glide.Glide


class Adapter(context: Context, wallpaperList: MutableList<ImageModel>) :
    RecyclerView.Adapter<Adapter.ViewHolder?>() {
    private val context: Context
    private val wallpaperList: MutableList<ImageModel>


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false) //!!!
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        Glide
            .with(context)
            .load(
                wallpaperList[position].urls!!.regular
//                    .getUrls()
//                    ?.getRegular()
            )
            .into(holder.imageView)
        holder.imageView.setOnClickListener { v ->
            val intent = Intent(v.getContext(), SetWallpaperActivity::class.java)
            intent.putExtra("image", "" + wallpaperList[position].urls!!.regular) //.getUrls()!!.getRegular())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            v.getContext().startActivity(intent)
        }
        holder.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(view.getContext(), SetWallpaperActivity::class.java)
                intent.putExtra("image", wallpaperList[position].urls!!.regular) //.getUrls()!!.getRegular())
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view.getContext().startActivity(intent)
                return
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: List<ImageModel>?) { //обновление адаптера
        wallpaperList.clear() //очищение старого списка
        wallpaperList.addAll(newList!!) //добавление новых элементов списка
        notifyDataSetChanged() //сообщение адаптеру об обновлении
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.image)
        }
    }

    init {
        this.context = context
        this.wallpaperList = wallpaperList
    }


 }