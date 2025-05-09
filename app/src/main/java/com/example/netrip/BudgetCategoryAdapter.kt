package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BudgetCategoryAdapter(
    private val categories: List<BudgetCategory>
) : RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewCategoryColor: View = view.findViewById(R.id.viewCategoryColor)
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val tvCategoryAmount: TextView = view.findViewById(R.id.tvCategoryAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.tvCategoryName.text = category.name
        holder.tvCategoryAmount.text = "$%.0f".format(category.amount)

        // Renk ayarlama örneği:
        val colorRes = when (category.name) {
            "Accommodation" -> R.color.categoryAccommodation
            "Food" -> R.color.categoryFood
            "Transportation" -> R.color.categoryTransportation
            "Activities" -> R.color.categoryActivities
            else -> R.color.categoryAccommodation
        }
        holder.viewCategoryColor.setBackgroundResource(colorRes)
    }

    override fun getItemCount() = categories.size
}
