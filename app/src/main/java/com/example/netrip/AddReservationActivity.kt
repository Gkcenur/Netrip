package com.example.netrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddReservationActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: Button
    private lateinit var reservationTypeButtons: List<ToggleButton>
    
    // Flight fields
    private lateinit var flightLayout: View
    private lateinit var etAirline: EditText
    private lateinit var etFlightNumber: EditText
    private lateinit var etFrom: EditText
    private lateinit var etTo: EditText
    private lateinit var etDepartureDate: EditText
    private lateinit var etDepartureTime: EditText
    private lateinit var etArrivalDate: EditText
    private lateinit var etArrivalTime: EditText
    
    // Hotel fields
    private lateinit var hotelLayout: View
    private lateinit var etHotelName: EditText
    private lateinit var etCheckInDate: EditText
    private lateinit var etCheckOutDate: EditText
    private lateinit var etRoomType: EditText
    private lateinit var etGuests: EditText
    
    // Activity fields
    private lateinit var activityLayout: View
    private lateinit var etActivityName: EditText
    private lateinit var etActivityDate: EditText
    private lateinit var etActivityTime: EditText
    private lateinit var etLocation: EditText
    private lateinit var etTickets: EditText
    
    private lateinit var etConfirmationNumber: EditText

    private val reservationTypes = listOf("Plane\nFlight", "Hotel", "Ticket\nActivity")
    private var selectedReservationType: String? = reservationTypes[0]

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reservation)

        initializeViews()
        setupReservationTypeButtons()
        setupDateAndTimePickers()
        setupClickListeners()
        
        // Show flight layout by default
        updateLayoutVisibility(0)
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        
        // Initialize layouts
        flightLayout = findViewById(R.id.flightLayout)
        hotelLayout = findViewById(R.id.hotelLayout)
        activityLayout = findViewById(R.id.activityLayout)
        
        // Initialize flight fields
        etAirline = findViewById(R.id.etAirline)
        etFlightNumber = findViewById(R.id.etFlightNumber)
        etFrom = findViewById(R.id.etFrom)
        etTo = findViewById(R.id.etTo)
        etDepartureDate = findViewById(R.id.etDepartureDate)
        etDepartureTime = findViewById(R.id.etDepartureTime)
        etArrivalDate = findViewById(R.id.etArrivalDate)
        etArrivalTime = findViewById(R.id.etArrivalTime)
        
        // Initialize hotel fields
        etHotelName = findViewById(R.id.etHotelName)
        etCheckInDate = findViewById(R.id.etCheckInDate)
        etCheckOutDate = findViewById(R.id.etCheckOutDate)
        etRoomType = findViewById(R.id.etRoomType)
        etGuests = findViewById(R.id.etGuests)
        
        // Initialize activity fields
        etActivityName = findViewById(R.id.etActivityName)
        etActivityDate = findViewById(R.id.etActivityDate)
        etActivityTime = findViewById(R.id.etActivityTime)
        etLocation = findViewById(R.id.etLocation)
        etTickets = findViewById(R.id.etTickets)
        
        etConfirmationNumber = findViewById(R.id.etConfirmationNumber)

        // Reservation type buttons
        reservationTypeButtons = listOf(
            findViewById(R.id.btnTypePlane),
            findViewById(R.id.btnTypeHotel),
            findViewById(R.id.btnTypeTicket)
        )
    }

    private fun setupReservationTypeButtons() {
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
                    // Update layout visibility
                    updateLayoutVisibility(index)
                } else {
                    button.setBackgroundResource(R.drawable.bg_card)
                    button.setTextColor(resources.getColor(R.color.topBar, null))
                }
            }
        }
        // Set the first type as selected by default
        reservationTypeButtons[0].isChecked = true
    }

    private fun updateLayoutVisibility(selectedIndex: Int) {
        // Hide all layouts first
        flightLayout.visibility = View.GONE
        hotelLayout.visibility = View.GONE
        activityLayout.visibility = View.GONE

        // Show selected layout
        when (selectedIndex) {
            0 -> flightLayout.visibility = View.VISIBLE
            1 -> hotelLayout.visibility = View.VISIBLE
            2 -> activityLayout.visibility = View.VISIBLE
        }
    }

    private fun setupDateAndTimePickers() {
        // Flight date/time pickers
        etDepartureDate.setOnClickListener { showDatePicker { date -> etDepartureDate.setText(date) } }
        etDepartureTime.setOnClickListener { showTimePicker { time -> etDepartureTime.setText(time) } }
        etArrivalDate.setOnClickListener { showDatePicker { date -> etArrivalDate.setText(date) } }
        etArrivalTime.setOnClickListener { showTimePicker { time -> etArrivalTime.setText(time) } }
        
        // Hotel date pickers
        etCheckInDate.setOnClickListener { showDatePicker { date -> etCheckInDate.setText(date) } }
        etCheckOutDate.setOnClickListener { showDatePicker { date -> etCheckOutDate.setText(date) } }
        
        // Activity date/time pickers
        etActivityDate.setOnClickListener { showDatePicker { date -> etActivityDate.setText(date) } }
        etActivityTime.setOnClickListener { showTimePicker { time -> etActivityTime.setText(time) } }
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveReservation() }

        // Airline selection dialog
        etAirline.setOnClickListener {
            val airlines = arrayOf("Air France", "Turkish Airlines", "Lufthansa", "Delta", "Qatar Airways")
            showSelectionDialog("Select Airline", airlines) { selected -> etAirline.setText(selected) }
        }

        // Hotel selection dialog
        etHotelName.setOnClickListener {
            val hotels = arrayOf("Grand Hotel Paris", "Hotel Plaza", "Hotel Royal", "Hotel Luxe", "Hotel Palace")
            showSelectionDialog("Select Hotel", hotels) { selected -> etHotelName.setText(selected) }
        }

        // Room type selection dialog
        etRoomType.setOnClickListener {
            val roomTypes = arrayOf("Standard", "Deluxe", "Suite", "Executive Suite", "Presidential Suite")
            showSelectionDialog("Select Room Type", roomTypes) { selected -> etRoomType.setText(selected) }
        }
    }

    private fun showSelectionDialog(title: String, items: Array<String>, onItemSelected: (String) -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setItems(items) { _, which ->
                onItemSelected(items[which])
            }
            .show()
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
        // TODO: Implement saving logic based on selected type
        Toast.makeText(this, "Reservation saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
