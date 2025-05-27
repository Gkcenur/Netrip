package com.example.netrip

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*
import android.text.Editable

class TravelPlannerActivity : BaseActivity() {
    private var currentDate = Calendar.getInstance() // Seçili gün
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: PlannerSectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        loadEvents()
    }

    override fun getLayoutId(): Int = R.layout.activity_travel_planner

    private fun setupUI() {
        db = FirebaseFirestore.getInstance()
        
        val rvPlannerEntries = findViewById<RecyclerView>(R.id.rvPlannerEntries)
        rvPlannerEntries.layoutManager = LinearLayoutManager(this)
        adapter = PlannerSectionAdapter(emptyList())
        rvPlannerEntries.adapter = adapter

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvDayName = findViewById<TextView>(R.id.tvDayName)
        val tvDayCount = findViewById<TextView>(R.id.tvDayCount)
        val btnPrevDay = findViewById<ImageView>(R.id.btnPrevDay)
        val btnNextDay = findViewById<ImageView>(R.id.btnNextDay)

        fun updateDateViews() {
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val dayNameFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            tvDate.text = dateFormat.format(currentDate.time)
            tvDayName.text = dayNameFormat.format(currentDate.time)
            tvDayCount.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate.time)
            loadEvents()
        }

        updateDateViews()

        tvDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    currentDate.set(year, month, dayOfMonth)
                    updateDateViews()
                    rvPlannerEntries.smoothScrollToPosition(0)
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnPrevDay.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, -1)
            updateDateViews()
        }

        btnNextDay.setOnClickListener {
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            updateDateViews()
        }

        // Profil butonuna tıklama
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val etSearch = findViewById<android.widget.EditText>(R.id.etSearch)
        var filteredSections = emptyList<PlannerSection>()
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                filteredSections = if (query.isEmpty()) {
                    emptyList()
                } else {
                    adapter.sections.filter {
                        it.title.lowercase().contains(query) ||
                        it.address.lowercase().contains(query) ||
                        (it.note?.lowercase()?.contains(query) ?: false)
                    }
                }
                adapter.updateSections(filteredSections)
            }
        })

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadEvents() {
        val startOfDay = currentDate.clone() as Calendar
        startOfDay.set(Calendar.HOUR_OF_DAY, 0)
        startOfDay.set(Calendar.MINUTE, 0)
        startOfDay.set(Calendar.SECOND, 0)
        startOfDay.set(Calendar.MILLISECOND, 0)
        val endOfDay = currentDate.clone() as Calendar
        endOfDay.set(Calendar.HOUR_OF_DAY, 23)
        endOfDay.set(Calendar.MINUTE, 59)
        endOfDay.set(Calendar.SECOND, 59)
        endOfDay.set(Calendar.MILLISECOND, 999)

        db.collection("events")
            .whereGreaterThanOrEqualTo("date", startOfDay.time)
            .whereLessThanOrEqualTo("date", endOfDay.time)
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val sections = documents.mapNotNull { doc ->
                    val data = doc.data
                    val title = data["title"] as? String ?: return@mapNotNull null
                    val address = data["location"] as? String ?: ""
                    val note = data["notes"] as? String
                    val section = data["timePeriod"] as? String ?: ""
                    val timeDate = data["time"]
                    val time: String = when (timeDate) {
                        is Date -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(timeDate)
                        is com.google.firebase.Timestamp -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(timeDate.toDate())
                        is Long -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timeDate))
                        else -> ""
                    }
                    PlannerSection(
                        section = section,
                        time = time,
                        title = title,
                        address = address,
                        note = note,
                        colorRes = R.color.orange
                    )
                }
                adapter.updateSections(sections)
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }
}
