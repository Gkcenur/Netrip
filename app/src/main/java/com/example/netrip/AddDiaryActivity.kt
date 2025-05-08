package com.example.netrip

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import android.view.View
import androidx.core.content.ContextCompat

class AddDiaryActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: Button
    private lateinit var etTitle: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var rvPhotos: RecyclerView
    private lateinit var etNotes: EditText
    private lateinit var layoutWeather: LinearLayout
    private lateinit var tvWeatherIcon: TextView
    private lateinit var etWeatherTemp: EditText
    private lateinit var layoutMood: LinearLayout
    private lateinit var tvMoodIcon: TextView
    private lateinit var tvMoodText: TextView
    private lateinit var layoutPlaces: LinearLayout
    private lateinit var btnAddPlace: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_diary)

        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        etTitle = findViewById(R.id.etTitle)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        rvPhotos = findViewById(R.id.rvPhotos)
        etNotes = findViewById(R.id.etNotes)
        layoutWeather = findViewById(R.id.layoutWeather)
        tvWeatherIcon = findViewById(R.id.tvWeatherIcon)
        etWeatherTemp = findViewById(R.id.etWeatherTemp)
        layoutMood = findViewById(R.id.layoutMood)
        tvMoodIcon = findViewById(R.id.tvMoodIcon)
        tvMoodText = findViewById(R.id.tvMoodText)
        layoutPlaces = findViewById(R.id.layoutPlaces)
        btnAddPlace = findViewById(R.id.btnAddPlace)

        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveDiaryEntry() }

        layoutWeather.setOnClickListener {
            val weatherOptions = arrayOf("☀️ Sunny", "🌧️ Rainy", "⛅ Partly Cloudy", "❄️ Snowy", "🌬️ Windy", "🌩️ Stormy")
            AlertDialog.Builder(this)
                .setTitle("Select Weather")
                .setItems(weatherOptions) { _, which ->
                    val selected = weatherOptions[which].split(" ")
                    tvWeatherIcon.text = selected[0]
                }
                .show()
        }

        layoutMood.setOnClickListener {
            val moodOptions = arrayOf("😊 Happy", "😢 Sad", "😎 Excited", "😴 Tired", "❤️ Loved it", "😐 Neutral")
            AlertDialog.Builder(this)
                .setTitle("Select Mood")
                .setItems(moodOptions) { _, which ->
                    val selected = moodOptions[which].split(" ", limit = 2)
                    tvMoodIcon.text = selected[0]
                    tvMoodText.text = selected[1]
                }
                .show()
        }

        btnAddPlace.setOnClickListener {
            val newPlace = EditText(this)
            newPlace.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 8.dp
            }
            newPlace.hint = "Place"
            newPlace.setPadding(12, 12, 12, 12)
            newPlace.textSize = 16f
            newPlace.setBackgroundResource(R.drawable.bg_card)
            newPlace.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location, 0, 0, 0)
            val divider = View(this)
            divider.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.dp
            )
            divider.setBackgroundColor(ContextCompat.getColor(this, R.color.diaryPeach))
            layoutPlaces.addView(divider)
            layoutPlaces.addView(newPlace)
        }
    }

    private fun saveDiaryEntry() {
        val selectedWeather = tvWeatherIcon.text.toString() + " " + etWeatherTemp.text.toString() + "°C"
        // Use selectedWeather as needed
        // TODO: Gather all data and save the diary entry
    }

    private fun getAllPlaces(): List<String> {
        val places = mutableListOf<String>()
        for (i in 0 until layoutPlaces.childCount) {
            val child = layoutPlaces.getChildAt(i)
            if (child is EditText) {
                val text = child.text.toString()
                if (text.isNotBlank()) places.add(text)
            }
        }
        return places
    }

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()
}
