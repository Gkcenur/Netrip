package com.example.netrip

import android.app.DatePickerDialog
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
import android.text.Editable

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

        val sections = List(20) {
            PlannerSection(
                "Section $it", "9:00 AM - 12:00 PM", "Activity $it",
                "Address $it", null, R.color.orange
            )
        }
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

        // Add calendar popup when clicking on the date
        tvDate.setOnClickListener {
            val cal = tripStart.clone() as Calendar
            cal.add(Calendar.DAY_OF_MONTH, currentDay - 1)
            
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }
                    
                    // Check if selected date is within trip range
                    if (selectedDate.timeInMillis >= tripStart.timeInMillis && 
                        selectedDate.timeInMillis <= tripEnd.timeInMillis) {
                        // Calculate the day number based on the selected date
                        val diffInMillis = selectedDate.timeInMillis - tripStart.timeInMillis
                        val diffInDays = (diffInMillis / (24 * 60 * 60 * 1000)).toInt() + 1
                        currentDay = diffInDays
                        updateDateViews()
                        
                        // Scroll to the selected day's events
                        rvPlannerEntries.smoothScrollToPosition(0)
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

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

        // Profil butonuna tıklama
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val etSearch = findViewById<android.widget.EditText>(R.id.etSearch)
        // Orijinal listeyi sakla
        var filteredSections = sections.toList()
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                filteredSections = if (query.isEmpty()) {
                    sections
                } else {
                    sections.filter {
                        it.title.lowercase().contains(query) ||
                        it.address.lowercase().contains(query) ||
                        (it.note?.lowercase()?.contains(query) ?: false)
                    }
                }
                rvPlannerEntries.adapter = PlannerSectionAdapter(filteredSections)
            }
        })
    }
}
