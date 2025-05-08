package com.example.netrip.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.R

class DiaryPhotoAdapter(private val photos: List<Uri>) : RecyclerView.Adapter<DiaryPhotoAdapter.PhotoViewHolder>() {
    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val uri = photos[position]
        holder.imageView.setImageURI(uri)
    }

    override fun getItemCount(): Int = photos.size
}
