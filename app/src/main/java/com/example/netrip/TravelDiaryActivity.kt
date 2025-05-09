package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TravelDiaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_diary)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddDiaryActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvDiaryEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DiaryEntryAdapter(
            listOf(
                DiaryEntry(
                    place = "Eiffel Tower",
                    date = "May 18, 2025",
                    description = "The view from the top was breathtaking! I could see the entire city of Paris. Worth every step of the climb!",
                    time = "14:30",
                    weatherRes = R.drawable.ic_weather_sun,
                    moodRes = R.drawable.ic_mood_happy,
                    likeRes = R.drawable.ic_heart,
                    photoBgRes = R.drawable.bg_diary_photo_orange,
                    headerBgRes = R.drawable.bg_diary_card_header_orange
                ),
                DiaryEntry(
                    place = "Louvre Museum",
                    date = "May 17, 2025",
                    description = "Finally saw the Mona Lisa! It was smaller than I expected but still amazing to see in person.",
                    time = "10:00",
                    weatherRes = R.drawable.ic_weather_sun,
                    moodRes = R.drawable.ic_mood_happy,
                    likeRes = R.drawable.ic_heart,
                    photoBgRes = R.drawable.bg_diary_photo_green,
                    headerBgRes = R.drawable.bg_diary_card_header_green
                )
            )
        )
    }
}
