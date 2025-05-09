package com.example.netrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentConversionAdapter(
    private val conversions: List<Conversion>
) : RecyclerView.Adapter<RecentConversionAdapter.ConversionViewHolder>() {

    class ConversionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvConversion: TextView = itemView.findViewById(R.id.tvConversion)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_conversion, parent, false)
        return ConversionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversionViewHolder, position: Int) {
        val conversion = conversions[position]
        holder.tvConversion.text = "${conversion.fromCurrency} → ${conversion.toCurrency}   ${formatAmount(conversion.fromAmount)} = ${formatAmount(conversion.toAmount)}"
        holder.tvDate.text = conversion.date
    }

    override fun getItemCount(): Int = conversions.size

    private fun formatAmount(amount: Double): String {
        return if (amount % 1.0 == 0.0) {
            amount.toInt().toString()
        } else {
            String.format("%.2f", amount)
        }
    }
}
