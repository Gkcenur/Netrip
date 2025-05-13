package com.example.netrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AddEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val btnDate = findViewById<Button>(R.id.btnDate)
        val btnTime = findViewById<Button>(R.id.btnTime)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etNotes = findViewById<EditText>(R.id.etNotes)

        val btnMorning = findViewById<TextView>(R.id.btnMorning)
        val btnAfternoon = findViewById<TextView>(R.id.btnAfternoon)
        val btnEvening = findViewById<TextView>(R.id.btnEvening)

        // Time period butonları için tıklama olayları
        btnMorning.setOnClickListener {
            btnMorning.isSelected = true
            btnAfternoon.isSelected = false
            btnEvening.isSelected = false
            Toast.makeText(this, "Morning selected", Toast.LENGTH_SHORT).show()
        }

        btnAfternoon.setOnClickListener {
            btnMorning.isSelected = false
            btnAfternoon.isSelected = true
            btnEvening.isSelected = false
            Toast.makeText(this, "Afternoon selected", Toast.LENGTH_SHORT).show()
        }

        btnEvening.setOnClickListener {
            btnMorning.isSelected = false
            btnAfternoon.isSelected = false
            btnEvening.isSelected = true
            Toast.makeText(this, "Evening selected", Toast.LENGTH_SHORT).show()
        }

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Date picker
        btnDate.setOnClickListener {
            val c = Calendar.getInstance()
            val dpd = DatePickerDialog(this, { _, year, month, day ->
                btnDate.text = "${month + 1}/$day/$year"
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            dpd.show()
        }

        // Time picker
        btnTime.setOnClickListener {
            val c = Calendar.getInstance()
            val tpd = TimePickerDialog(this, { _, hour, minute ->
                btnTime.text = String.format("%02d:%02d", hour, minute)
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
            tpd.show()
        }

        // Intent ile gelen verileri al ve alanlara yerleştir
        intent.getStringExtra("title")?.let { etTitle.setText(it) }
        intent.getStringExtra("address")?.let { etLocation.setText(it) }
        intent.getStringExtra("note")?.let { etNotes.setText(it) }
        intent.getStringExtra("time")?.let { btnTime.text = it }
        
        // Section bilgisini kullanarak zaman dilimi butonunu seçili yap
        val section = intent.getStringExtra("section")
        when (section) {
            "Morning", "Section 0", "Section 1" -> {
                btnMorning.isSelected = true
                btnAfternoon.isSelected = false
                btnEvening.isSelected = false
            }
            "Afternoon", "Section 2", "Section 3" -> {
                btnMorning.isSelected = false
                btnAfternoon.isSelected = true
                btnEvening.isSelected = false
            }
            "Evening", "Section 4", "Section 5" -> {
                btnMorning.isSelected = false
                btnAfternoon.isSelected = false
                btnEvening.isSelected = true
            }
        }

        btnSave.setOnClickListener {
            // Kaydetme işlemleri burada yapılacak
            Toast.makeText(this, "Event saved!", Toast.LENGTH_SHORT).show()
        }
    }
}