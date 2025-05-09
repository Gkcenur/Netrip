package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlannerEntryAdapter(
    private val entries: List<PlannerEntry>,
    private val onFavoriteClick: (Int) -> Unit
) : RecyclerView.Adapter<PlannerEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivType: ImageView = view.findViewById(R.id.ivType)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivFavorite: ImageView = view.findViewById(R.id.ivFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planner_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.ivType.setImageResource(entry.typeIcon)
        holder.tvTitle.text = entry.title
        holder.tvTime.text = entry.time
        holder.tvDescription.text = entry.description
        holder.ivFavorite.setImageResource(
            if (entry.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart
        )
        holder.ivFavorite.setOnClickListener { onFavoriteClick(position) }
    }

    override fun getItemCount() = entries.size
}
