package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.adapter.ReservationAdapter
import com.example.netrip.model.Reservation
import com.example.netrip.model.ReservationType

class ReservationsActivity : AppCompatActivity() {
    private lateinit var adapter: ReservationAdapter
    private lateinit var allReservations: List<Reservation>
    private lateinit var chipButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)

        // Örnek veri
        allReservations = listOf(
            Reservation("1", ReservationType.FLIGHT, "Air France", "Flight AF1234", "May 15, 2025 - 10:30 AM", "New York (JFK) → Paris (CDG)", "Confirmed", "AF"),
            Reservation("2", ReservationType.HOTEL, "Grand Hotel Paris", "Deluxe Room - 2 Guests", "May 15 - 25, 2025 (10 nights)", "15 Rue de Rivoli, Paris", "Confirmed", "H"),
            Reservation("3", ReservationType.ACTIVITY, "Eiffel Tower Tour", "Skip-the-line Tickets - 2 Adults", "May 18, 2025 - 2:00 PM", "Champ de Mars, Paris", "Confirmed", "ET")
        )

        val rvReservations = findViewById<RecyclerView>(R.id.rvReservations)
        adapter = ReservationAdapter(allReservations) { reservation ->
            val intent = Intent(this, ReservationDetailsActivity::class.java)
            startActivity(intent)
        }
        rvReservations.layoutManager = LinearLayoutManager(this)
        rvReservations.adapter = adapter

        // Chip buttons setup
        chipButtons = listOf(
            findViewById(R.id.chipAll),
            findViewById(R.id.chipFlights),
            findViewById(R.id.chipHotels),
            findViewById(R.id.chipActivities)
        )

        // Set initial state
        updateChipSelection(0)

        // Chip click listeners
        chipButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                updateChipSelection(index)
                when (index) {
                    0 -> adapter.updateList(allReservations)
                    1 -> adapter.updateList(allReservations.filter { it.type == ReservationType.FLIGHT })
                    2 -> adapter.updateList(allReservations.filter { it.type == ReservationType.HOTEL })
                    3 -> adapter.updateList(allReservations.filter { it.type == ReservationType.ACTIVITY })
                }
            }
        }

        // Navigation buttons
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnAddReservation).setOnClickListener {
            startActivity(Intent(this, AddReservationActivity::class.java))
        }

        // Search functionality
        findViewById<EditText>(R.id.etSearch).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                adapter.updateList(allReservations.filter {
                    it.title.lowercase().contains(query) ||
                    it.subtitle.lowercase().contains(query) ||
                    it.location.lowercase().contains(query)
                })
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateChipSelection(selectedIndex: Int) {
        chipButtons.forEachIndexed { index, button ->
            button.setBackgroundResource(
                if (index == selectedIndex) R.drawable.bg_chip_selected
                else R.drawable.bg_chip
            )
            button.setTextColor(
                if (index == selectedIndex) resources.getColor(android.R.color.white, null)
                else resources.getColor(R.color.topBar, null)
            )
        }
    }
}
