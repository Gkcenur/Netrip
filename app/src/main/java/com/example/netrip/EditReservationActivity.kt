package com.example.netrip

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditReservationActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private var reservationId: String? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reservation)

        // FLIGHT
        val etAirline = findViewById<EditText>(R.id.etAirline)
        val etFlightNumber = findViewById<EditText>(R.id.etFlightNumber)
        val etDeparture = findViewById<EditText>(R.id.etDeparture)
        val etArrival = findViewById<EditText>(R.id.etArrival)
        val etDepartureDate = findViewById<EditText>(R.id.etDepartureDate)
        val etArrivalDate = findViewById<EditText>(R.id.etArrivalDate)
        // HOTEL
        val etHotelName = findViewById<EditText>(R.id.etHotelName)
        val etRoomType = findViewById<EditText>(R.id.etRoomType)
        val etGuests = findViewById<EditText>(R.id.etGuests)
        val etCheckIn = findViewById<EditText>(R.id.etCheckIn)
        val etCheckOut = findViewById<EditText>(R.id.etCheckOut)
        // ACTIVITY
        val etActivityName = findViewById<EditText>(R.id.etActivityName)
        val etTickets = findViewById<EditText>(R.id.etTickets)
        val etActivityDate = findViewById<EditText>(R.id.etActivityDate)
        val etActivityTime = findViewById<EditText>(R.id.etActivityTime)
        val etActivityLocation = findViewById<EditText>(R.id.etActivityLocation)

        val btnSave = findViewById<Button>(R.id.btnSave)

        // Başta tüm alanları gizle
        val allFields = listOf(
            etAirline, etFlightNumber, etDeparture, etArrival, etDepartureDate, etArrivalDate,
            etHotelName, etRoomType, etGuests, etCheckIn, etCheckOut,
            etActivityName, etTickets, etActivityDate, etActivityTime, etActivityLocation
        )
        allFields.forEach { it.visibility = View.GONE }

        reservationId = intent.getStringExtra("reservationId")
        if (reservationId != null) {
            db.collection("reservations").document(reservationId!!).get()
                .addOnSuccessListener { doc ->
                    val details = doc.get("details") as? Map<*, *>
                    type = doc.getString("type")?.lowercase()

                    when (type) {
                        "flight" -> {
                            etAirline.visibility = View.VISIBLE
                            etFlightNumber.visibility = View.VISIBLE
                            etDeparture.visibility = View.VISIBLE
                            etArrival.visibility = View.VISIBLE
                            etDepartureDate.visibility = View.VISIBLE
                            etArrivalDate.visibility = View.VISIBLE

                            etAirline.setText(details?.get("airline")?.toString() ?: "")
                            etFlightNumber.setText(details?.get("flightNumber")?.toString() ?: "")
                            etDeparture.setText(details?.get("departureLocation")?.toString() ?: "")
                            etArrival.setText(details?.get("arrivalLocation")?.toString() ?: "")
                            etDepartureDate.setText(details?.get("departureDate")?.toString() ?: "")
                            etArrivalDate.setText(details?.get("arrivalDate")?.toString() ?: "")
                        }
                        "hotel" -> {
                            etHotelName.visibility = View.VISIBLE
                            etRoomType.visibility = View.VISIBLE
                            etGuests.visibility = View.VISIBLE
                            etCheckIn.visibility = View.VISIBLE
                            etCheckOut.visibility = View.VISIBLE

                            etHotelName.setText(details?.get("name")?.toString() ?: "")
                            etRoomType.setText(details?.get("roomType")?.toString() ?: "")
                            etGuests.setText(details?.get("numberOfGuests")?.toString() ?: "")
                            etCheckIn.setText(details?.get("checkInDate")?.toString() ?: "")
                            etCheckOut.setText(details?.get("checkOutDate")?.toString() ?: "")
                        }
                        "activity" -> {
                            etActivityName.visibility = View.VISIBLE
                            etTickets.visibility = View.VISIBLE
                            etActivityDate.visibility = View.VISIBLE
                            etActivityTime.visibility = View.VISIBLE
                            etActivityLocation.visibility = View.VISIBLE

                            etActivityName.setText(details?.get("name")?.toString() ?: "")
                            etTickets.setText(details?.get("numberOfTickets")?.toString() ?: "")
                            etActivityDate.setText(details?.get("date")?.toString() ?: "")
                            etActivityTime.setText(details?.get("time")?.toString() ?: "")
                            etActivityLocation.setText(details?.get("location")?.toString() ?: "")
                        }
                    }
                }
        }

        btnSave.setOnClickListener {
            val updatedDetails = when (type) {
                "flight" -> mapOf(
                    "airline" to etAirline.text.toString(),
                    "flightNumber" to etFlightNumber.text.toString(),
                    "departureLocation" to etDeparture.text.toString(),
                    "arrivalLocation" to etArrival.text.toString(),
                    "departureDate" to etDepartureDate.text.toString(),
                    "arrivalDate" to etArrivalDate.text.toString()
                )
                "hotel" -> mapOf(
                    "name" to etHotelName.text.toString(),
                    "roomType" to etRoomType.text.toString(),
                    "numberOfGuests" to etGuests.text.toString(),
                    "checkInDate" to etCheckIn.text.toString(),
                    "checkOutDate" to etCheckOut.text.toString()
                )
                "activity" -> mapOf(
                    "name" to etActivityName.text.toString(),
                    "numberOfTickets" to etTickets.text.toString(),
                    "date" to etActivityDate.text.toString(),
                    "time" to etActivityTime.text.toString(),
                    "location" to etActivityLocation.text.toString()
                )
                else -> emptyMap()
            }
            db.collection("reservations").document(reservationId!!).update("details", updatedDetails)
                .addOnSuccessListener {
                    Toast.makeText(this, "Güncellendi!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Hata oluştu!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
