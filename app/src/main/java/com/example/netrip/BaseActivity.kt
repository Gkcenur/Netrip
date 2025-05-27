package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setupBottomNavigation()
    }

    abstract fun getLayoutId(): Int

    private fun setupBottomNavigation() {
        // Home navigation
        findViewById<LinearLayout>(R.id.navHome)?.setOnClickListener {
            if (this !is HomeActivity) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        // Travel Diary navigation
        findViewById<LinearLayout>(R.id.navDiary)?.setOnClickListener {
            if (this !is TravelDiaryActivity) {
                startActivity(Intent(this, TravelDiaryActivity::class.java))
                finish()
            }
        }

        // Travel Planner navigation
        findViewById<LinearLayout>(R.id.navPlanner)?.setOnClickListener {
            if (this !is TravelPlannerActivity) {
                startActivity(Intent(this, TravelPlannerActivity::class.java))
                finish()
            }
        }

        // Reservations navigation
        findViewById<LinearLayout>(R.id.navReservations)?.setOnClickListener {
            if (this !is ReservationsActivity) {
                startActivity(Intent(this, ReservationsActivity::class.java))
                finish()
            }
        }
    }
} 