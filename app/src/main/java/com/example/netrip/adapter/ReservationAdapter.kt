package com.example.netrip.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.R
import com.example.netrip.model.Reservation
import com.example.netrip.model.ReservationType

class ReservationAdapter(
    private var reservations: List<Reservation>,
    private val onClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInitials: TextView = view.findViewById(R.id.tvInitials)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
        val tvDateInfo: TextView = view.findViewById(R.id.tvDateInfo)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
        val colorBar: View = view.findViewById(R.id.colorBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val item = reservations[position]
        holder.tvInitials.text = item.initials
        holder.tvTitle.text = item.title
        holder.tvSubtitle.text = item.subtitle
        holder.tvDateInfo.text = item.dateInfo
        holder.tvLocation.text = item.location
        holder.tvStatus.text = item.status

        // Renkli bar tipi - yeni tasarıma uygun renkler
        when (item.type) {
            ReservationType.FLIGHT -> holder.colorBar.setBackgroundResource(R.color.orange)
            ReservationType.HOTEL -> holder.colorBar.setBackgroundResource(R.color.categoryAccommodation)
            ReservationType.ACTIVITY -> holder.colorBar.setBackgroundResource(R.color.emergencyGreen)
        }

        // Durum etiketi arka planı - yeni tasarıma uygun drawable
        holder.tvStatus.background = holder.itemView.context.getDrawable(R.drawable.bg_confirmed_rounded)

        // Kart arka planı - yeni tasarıma uygun drawable
        holder.itemView.background = holder.itemView.context.getDrawable(R.drawable.bg_card_white_rounded_shadow)

        holder.itemView.setOnClickListener { onClick(item) }
        holder.btnAdd.setOnClickListener { /* ekleme işlemi */ }
    }

    override fun getItemCount() = reservations.size

    fun updateList(newList: List<Reservation>) {
        reservations = newList
        notifyDataSetChanged()
    }
}
