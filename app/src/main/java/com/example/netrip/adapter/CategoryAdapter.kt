package com.example.netrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.R
import com.example.netrip.model.Category

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIcon: TextView = itemView.findViewById(R.id.tvCategoryIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val tvAmount: TextView = itemView.findViewById(R.id.tvCategoryAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.tvIcon.text = category.iconLetter
        holder.tvName.text = category.name
        holder.tvAmount.text = "$ ${category.amount}"
    }

    override fun getItemCount(): Int = categories.size
}
