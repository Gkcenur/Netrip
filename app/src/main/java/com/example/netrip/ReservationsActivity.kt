package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.adapter.ReservationAdapter
import com.example.netrip.model.Reservation
import com.example.netrip.model.ReservationType
import com.google.firebase.firestore.FirebaseFirestore

class ReservationsActivity : BaseActivity() {
    private lateinit var adapter: ReservationAdapter
    private lateinit var allReservations: List<Reservation>
    private lateinit var chipButtons: List<Button>
    private val db = FirebaseFirestore.getInstance()
    private val reservations = mutableListOf<Reservation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView kaldırıldı, BaseActivity hallediyor
        // Diğer UI işlemleri
        // ...

        val rvReservations = findViewById<RecyclerView>(R.id.rvReservations)
        adapter = ReservationAdapter(reservations) { reservation ->
            // Tıklanınca yapılacaklar (ör: detay sayfasına git)
        }
        rvReservations.layoutManager = LinearLayoutManager(this)
        rvReservations.adapter = adapter

        fetchReservations()

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
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnAddReservation).setOnClickListener {
            startActivity(Intent(this, AddReservationActivity::class.java))
        }
        findViewById<ImageView>(R.id.ivProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Search functionality
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch)
        btnSearch.setOnClickListener {
            val editText = EditText(this)
            editText.hint = "Search..."
            editText.setSingleLine()
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Search Reservations")
                .setView(editText)
                .setPositiveButton("Search") { dialog, _ ->
                    val query = editText.text.toString().lowercase()
                    adapter.updateList(allReservations.filter {
                        it.title.lowercase().contains(query) ||
                        it.subtitle.lowercase().contains(query) ||
                        it.location.lowercase().contains(query)
                    })
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun fetchReservations() {
        db.collection("reservations").get()
            .addOnSuccessListener { snapshot ->
                reservations.clear()
                for (doc in snapshot.documents) {
                    val typeString = doc.getString("type") ?: "FLIGHT"
                    val type = when (typeString.lowercase()) {
                        "flight" -> ReservationType.FLIGHT
                        "hotel" -> ReservationType.HOTEL
                        "activity" -> ReservationType.ACTIVITY
                        else -> ReservationType.FLIGHT
                    }
                    val details = doc.get("details") as? Map<*, *> ?: emptyMap<String, Any>()

                    var title = ""
                    var subtitle = ""
                    var date = ""
                    var location = ""
                    var status = "Confirmed"
                    var initials = ""

                    when (type) {
                        ReservationType.FLIGHT -> {
                            val airline = details["airline"]?.toString() ?: ""
                            val flightNumber = details["flightNumber"]?.toString() ?: ""
                            val departure = details["departureLocation"]?.toString() ?: ""
                            val arrival = details["arrivalLocation"]?.toString() ?: ""
                            val departureDate = details["departureDate"]?.toString() ?: ""
                            val arrivalDate = details["arrivalDate"]?.toString() ?: ""
                            initials = airline.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString("").take(2).uppercase()
                            title = airline
                            subtitle = "Flight $flightNumber"
                            date = "$departureDate - $arrivalDate"
                            location = "$departure → $arrival"
                        }
                        ReservationType.HOTEL -> {
                            val name = details["name"]?.toString() ?: ""
                            val roomType = details["roomType"]?.toString() ?: ""
                            val guests = details["numberOfGuests"]?.toString() ?: ""
                            val checkIn = details["checkInDate"]?.toString() ?: ""
                            val checkOut = details["checkOutDate"]?.toString() ?: ""
                            initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString("").take(2).uppercase()
                            title = name
                            subtitle = "$roomType - $guests Guests"
                            date = "$checkIn - $checkOut"
                            location = "" // Otel adresi yoksa boş bırak
                        }
                        ReservationType.ACTIVITY -> {
                            val name = details["name"]?.toString() ?: ""
                            val tickets = details["numberOfTickets"]?.toString() ?: ""
                            val dateStr = details["date"]?.toString() ?: ""
                            val time = details["time"]?.toString() ?: ""
                            val loc = details["location"]?.toString() ?: ""
                            initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString("").take(2).uppercase()
                            title = name
                            subtitle = "$tickets Tickets"
                            date = "$dateStr $time"
                            location = loc
                        }
                    }

                    reservations.add(
                        Reservation(
                            id = doc.id,
                            type = type,
                            title = title,
                            subtitle = subtitle,
                            date = date,
                            location = location,
                            status = status,
                            initials = initials
                        )
                    )
                }
                allReservations = reservations.toList()
                adapter.updateList(allReservations)
            }
    }

    override fun getLayoutId(): Int = R.layout.activity_reservations

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

    fun filterByType(type: String) {
        val filtered = when (type.lowercase()) {
            "all" -> reservations
            "flight" -> reservations.filter { it.type == ReservationType.FLIGHT }
            "hotel" -> reservations.filter { it.type == ReservationType.HOTEL }
            "activity" -> reservations.filter { it.type == ReservationType.ACTIVITY }
            else -> reservations
        }
        adapter.updateList(filtered)
    }
}
