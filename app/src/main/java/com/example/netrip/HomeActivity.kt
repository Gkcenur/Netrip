package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        val budgetButton = findViewById<LinearLayout>(R.id.quickAccessBudget)
        budgetButton.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }
        val emergencyButton = findViewById<LinearLayout>(R.id.quickAccessEmergency)
        emergencyButton.setOnClickListener {
            val intent = Intent(this, EmergencyInfoActivity::class.java)
            startActivity(intent)
        }
        val currencyConverterButton = findViewById<LinearLayout>(R.id.quickAccessCurrencyConverter)
        currencyConverterButton.setOnClickListener {
            val intent = Intent(this, CurrencyConverterActivity::class.java)
            startActivity(intent)
        }
        val reservationButton = findViewById<LinearLayout>(R.id.quickAccessReservation)
        reservationButton.setOnClickListener {
            val intent = Intent(this, ReservationDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
