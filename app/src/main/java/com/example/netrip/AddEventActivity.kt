package com.example.netrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class AddEventActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var selectedDate: Calendar = Calendar.getInstance()
    private var selectedTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        db = FirebaseFirestore.getInstance()

        val btnDate = findViewById<Button>(R.id.btnDate)
        val btnTime = findViewById<Button>(R.id.btnTime)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val btnMorning = findViewById<TextView>(R.id.btnMorning)
        val btnAfternoon = findViewById<TextView>(R.id.btnAfternoon)
        val btnEvening = findViewById<TextView>(R.id.btnEvening)

        // Geri tuşu
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Alt menü (bottom navigation) tıklama olayları
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navDiary)?.setOnClickListener {
            startActivity(Intent(this, TravelDiaryActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navPlanner)?.setOnClickListener {
            startActivity(Intent(this, TravelPlannerActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.navReservations)?.setOnClickListener {
            startActivity(Intent(this, ReservationsActivity::class.java))
        }

        // Time period butonları için tıklama olayları
        btnMorning.setOnClickListener {
            btnMorning.isSelected = true
            btnAfternoon.isSelected = false
            btnEvening.isSelected = false
        }

        btnAfternoon.setOnClickListener {
            btnMorning.isSelected = false
            btnAfternoon.isSelected = true
            btnEvening.isSelected = false
        }

        btnEvening.setOnClickListener {
            btnMorning.isSelected = false
            btnAfternoon.isSelected = false
            btnEvening.isSelected = true
        }

        // Date picker
        btnDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    selectedDate.set(year, month, day)
                    btnDate.text = "${day}/${month + 1}/${year}"
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Time picker
        btnTime.setOnClickListener {
            TimePickerDialog(
                this,
                { _, hour, minute ->
                    selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                    selectedTime.set(Calendar.MINUTE, minute)
                    btnTime.text = String.format("%02d:%02d", hour, minute)
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Save button
        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val location = etLocation.text.toString()
            val notes = etNotes.text.toString()
            
            // Get selected time period
            val timePeriod = when {
                btnMorning.isSelected -> "Morning"
                btnAfternoon.isSelected -> "Afternoon"
                btnEvening.isSelected -> "Evening"
                else -> "Morning" // Default
            }

            if (title.isNotEmpty() && location.isNotEmpty()) {
                val event = hashMapOf(
                    "title" to title,
                    "location" to location,
                    "notes" to notes,
                    "date" to selectedDate.time,
                    "time" to selectedTime.time,
                    "timePeriod" to timePeriod,
                    "createdAt" to Date()
                )

                db.collection("events")
                    .add(event)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Event saved successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error saving event: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}