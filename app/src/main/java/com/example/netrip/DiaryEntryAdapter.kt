package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryEntryAdapter(private val items: List<DiaryEntry>) :
    RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder>() {

    class DiaryEntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerBg: View = view.findViewById(R.id.headerBg)
        val tvPlace: TextView = view.findViewById(R.id.tvPlace)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val photoRow: ViewGroup = view.findViewById(R.id.photoRow)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val ivWeather: ImageView = view.findViewById(R.id.ivWeather)
        val ivMood: ImageView = view.findViewById(R.id.ivMood)
        val ivLike: ImageView = view.findViewById(R.id.ivLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary_entry, parent, false)
        return DiaryEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryEntryViewHolder, position: Int) {
        val item = items[position]
        holder.headerBg.setBackgroundResource(item.headerBgRes)
        holder.tvPlace.text = item.place
        holder.tvDate.text = item.date
        for (i in 0 until holder.photoRow.childCount) {
            holder.photoRow.getChildAt(i).setBackgroundResource(item.photoBgRes)
        }
        holder.tvDescription.text = item.description
        holder.tvTime.text = item.time
        holder.ivWeather.setImageResource(item.weatherRes)
        holder.ivMood.setImageResource(item.moodRes)
        holder.ivLike.setImageResource(item.likeRes)
    }

    override fun getItemCount() = items.size
}
