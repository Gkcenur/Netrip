package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryEntryAdapter(
    private val items: List<DiaryEntry>,
    private val onDeleteClick: (DiaryEntry) -> Unit
) :
    RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder>() {

    class DiaryEntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlace: TextView = view.findViewById(R.id.tvPlace)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val ivDelete: android.widget.ImageView = view.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary_entry, parent, false)
        return DiaryEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryEntryViewHolder, position: Int) {
        val item = items[position]
        holder.tvPlace.text = item.title
        holder.tvDate.text = item.date
        holder.tvDescription.text = item.notes
        holder.tvTime.text = item.time
        holder.ivDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount() = items.size
}
