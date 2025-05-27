package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupQuickAccessButtons()
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    private fun setupQuickAccessButtons() {
        val travelDiaryButton = findViewById<LinearLayout>(R.id.quickAccessTravelDiary)
        travelDiaryButton.setOnClickListener {
            startActivity(Intent(this, TravelDiaryActivity::class.java))
        }

        val travelPlannerButton = findViewById<LinearLayout>(R.id.quickAccessTravelPlanner)
        travelPlannerButton.setOnClickListener {
            startActivity(Intent(this, TravelPlannerActivity::class.java))
        }

        val budgetButton = findViewById<LinearLayout>(R.id.quickAccessBudget)
        budgetButton.setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }

        val emergencyButton = findViewById<LinearLayout>(R.id.quickAccessEmergency)
        emergencyButton.setOnClickListener {
            startActivity(Intent(this, EmergencyInfoActivity::class.java))
        }

        val currencyConverterButton = findViewById<LinearLayout>(R.id.quickAccessCurrencyConverter)
        currencyConverterButton.setOnClickListener {
            startActivity(Intent(this, CurrencyConverterActivity::class.java))
        }

        val reservationButton = findViewById<LinearLayout>(R.id.quickAccessReservation)
        reservationButton.setOnClickListener {
            startActivity(Intent(this, ReservationsActivity::class.java))
        }
    }
}
