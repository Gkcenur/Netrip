package com.example.netrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddReservationActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: Button
    private lateinit var reservationTypeButtons: List<ToggleButton>
    private lateinit var etAirline: EditText
    private lateinit var etFlightNumber: EditText
    private lateinit var etFrom: EditText
    private lateinit var etTo: EditText
    private lateinit var etDepartureDate: EditText
    private lateinit var etDepartureTime: EditText
    private lateinit var etArrivalDate: EditText
    private lateinit var etArrivalTime: EditText
    private lateinit var etConfirmationNumber: EditText

    private val reservationTypes = listOf("Plane\nFlight", "Hotel", "Car\nTransport", "Ticket\nActivity")
    private var selectedReservationType: String? = reservationTypes[0]

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reservation)

        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        etAirline = findViewById(R.id.etAirline)
        etFlightNumber = findViewById(R.id.etFlightNumber)
        etFrom = findViewById(R.id.etFrom)
        etTo = findViewById(R.id.etTo)
        etDepartureDate = findViewById(R.id.etDepartureDate)
        etDepartureTime = findViewById(R.id.etDepartureTime)
        etArrivalDate = findViewById(R.id.etArrivalDate)
        etArrivalTime = findViewById(R.id.etArrivalTime)
        etConfirmationNumber = findViewById(R.id.etConfirmationNumber)

        // Reservation type buttons
        reservationTypeButtons = listOf(
            findViewById(R.id.btnTypePlane),
            findViewById(R.id.btnTypeHotel),
            findViewById(R.id.btnTypeCar),
            findViewById(R.id.btnTypeTicket)
        )

        // Set up reservation type selection
        reservationTypeButtons.forEachIndexed { index, button ->
            button.text = reservationTypes[index]
            button.textOn = reservationTypes[index]
            button.textOff = reservationTypes[index]
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedReservationType = reservationTypes[index]
                    // Uncheck others and reset their style
                    reservationTypeButtons.forEachIndexed { i, btn ->
                        if (i != index) {
                            btn.isChecked = false
                            btn.setBackgroundResource(R.drawable.bg_card)
                            btn.setTextColor(resources.getColor(R.color.topBar, null))
                        }
                    }
                    // Set active style
                    button.setBackgroundResource(R.drawable.bg_card_active)
                    button.setTextColor(resources.getColor(android.R.color.black, null))
                } else {
                    button.setBackgroundResource(R.drawable.bg_card)
                    button.setTextColor(resources.getColor(R.color.topBar, null))
                }
            }
        }
        // Set the first type as selected by default
        reservationTypeButtons[0].isChecked = true

        // Date and time pickers
        etDepartureDate.setOnClickListener {
            showDatePicker { date -> etDepartureDate.setText(date) }
        }
        etDepartureTime.setOnClickListener {
            showTimePicker { time -> etDepartureTime.setText(time) }
        }
        etArrivalDate.setOnClickListener {
            showDatePicker { date -> etArrivalDate.setText(date) }
        }
        etArrivalTime.setOnClickListener {
            showTimePicker { time -> etArrivalTime.setText(time) }
        }

        // Example: Airline selection dialog
        etAirline.setOnClickListener {
            val airlines = arrayOf("Air France", "Turkish Airlines", "Lufthansa", "Delta", "Qatar Airways")
            AlertDialog.Builder(this)
                .setTitle("Select Airline")
                .setItems(airlines) { _, which ->
                    etAirline.setText(airlines[which])
                }
                .show()
        }

        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveReservation() }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                onTimeSelected(timeFormat.format(calendar.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun saveReservation() {
        // Gather all data and save the reservation
        // TODO: Implement saving logic
        Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
