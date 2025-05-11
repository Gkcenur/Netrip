package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TravelPlannerActivity : AppCompatActivity() {
    private val tripStart = Calendar.getInstance().apply { set(2025, 4, 15) } // May 15, 2025
    private val tripEnd = Calendar.getInstance().apply { set(2025, 4, 25) }   // May 25, 2025
    private var currentDay = 4 // Örnek: 4. gün
    private val totalDays = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_planner)

        val rvPlannerEntries = findViewById<RecyclerView>(R.id.rvPlannerEntries)
        rvPlannerEntries.layoutManager = LinearLayoutManager(this)

        val sections = listOf(
            PlannerSection(
                "Morning", "9:00 AM - 12:00 PM", "Eiffel Tower Visit",
                "Champ de Mars, 5 Av. Anatole France", null, R.color.orange
            ),
            PlannerSection(
                "Afternoon", "2:00 PM - 5:00 PM", "Louvre Museum",
                "Rue de Rivoli, 75001 Paris", "Note: Skip-the-line tickets booked", R.color.green
            ),
            PlannerSection(
                "Evening", "8:00 PM - 10:00 PM", "Dinner at Le Jules Verne",
                "Eiffel Tower, 2nd Floor", null, R.color.orange
            )
        )
        rvPlannerEntries.adapter = PlannerSectionAdapter(sections)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvDayName = findViewById<TextView>(R.id.tvDayName)
        val tvDayCount = findViewById<TextView>(R.id.tvDayCount)
        val btnPrevDay = findViewById<ImageView>(R.id.btnPrevDay)
        val btnNextDay = findViewById<ImageView>(R.id.btnNextDay)

        // Başlangıç tarihi + currentDay-1 kadar gün ekle
        fun updateDateViews() {
            val cal = tripStart.clone() as Calendar
            cal.add(Calendar.DAY_OF_MONTH, currentDay - 1)
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val dayNameFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            tvDate.text = dateFormat.format(cal.time)
            tvDayName.text = dayNameFormat.format(cal.time)
            tvDayCount.text = "Day $currentDay of $totalDays"
        }

        updateDateViews()

        btnPrevDay.setOnClickListener {
            if (currentDay > 1) {
                currentDay--
                updateDateViews()
            }
        }

        btnNextDay.setOnClickListener {
            if (currentDay < totalDays) {
                currentDay++
                updateDateViews()
            }
        }

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }
}
