package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlannerSectionAdapter(
    private val sections: List<PlannerSection>
) : RecyclerView.Adapter<PlannerSectionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sectionColor: View = view.findViewById(R.id.sectionColor)
        val tvSection: TextView = view.findViewById(R.id.tvSection)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvNote: TextView = view.findViewById(R.id.tvNote)
        val btnAddCircle: ImageButton = view.findViewById(R.id.btnAddCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planner_section, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sections[position]
        holder.sectionColor.setBackgroundResource(section.colorRes)
        holder.tvSection.text = section.section
        holder.tvSection.setTextColor(holder.itemView.context.getColor(section.colorRes))
        holder.tvTime.text = section.time
        holder.tvTitle.text = section.title
        holder.tvAddress.text = section.address
        holder.tvNote.text = section.note ?: ""
    }

    override fun getItemCount() = sections.size
}
