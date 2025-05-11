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

        val btnMorning = findViewById<Button>(R.id.btnMorning)
        val btnAfternoon = findViewById<Button>(R.id.btnAfternoon)
        val btnEvening = findViewById<Button>(R.id.btnEvening)

        val periodButtons = listOf(btnMorning, btnAfternoon, btnEvening)

        periodButtons.forEach { btn ->
            btn.setOnClickListener {
                periodButtons.forEach { it.setBackgroundResource(R.drawable.rounded_edittext) }
                btn.setBackgroundResource(R.drawable.rounded_selected)
            }
        }

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Bu, mevcut ekranı kapatıp bir önceki ekrana döner
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

        btnSave.setOnClickListener {
            // Kaydetme işlemleri burada yapılacak
            Toast.makeText(this, "Event saved!", Toast.LENGTH_SHORT).show()
        }
    }
}