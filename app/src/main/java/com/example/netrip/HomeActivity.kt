package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        val travelDiaryButton = findViewById<LinearLayout>(R.id.quickAccessTravelDiary)
        travelDiaryButton.setOnClickListener {
            val intent = Intent(this, TravelDiaryActivity::class.java)
            startActivity(intent)
        }
        val travelPlannerButton = findViewById<LinearLayout>(R.id.quickAccessTravelPlanner)
        travelPlannerButton.setOnClickListener {
            val intent = Intent(this, TravelPlannerActivity::class.java)
            startActivity(intent)
        }
    }
}
