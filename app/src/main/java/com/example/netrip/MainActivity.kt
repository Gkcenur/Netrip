package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.btnOpenAddBudget).setOnClickListener {
            startActivity(Intent(this, AddBudgetActivity::class.java))
        }
        findViewById<Button>(R.id.btnOpenAddDiary).setOnClickListener {
            startActivity(Intent(this, AddDiaryActivity::class.java))
        }
        findViewById<Button>(R.id.btnOpenAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
        findViewById<Button>(R.id.btnOpenAddReservation).setOnClickListener {
            startActivity(Intent(this, AddReservationActivity::class.java))
        }
        findViewById<Button>(R.id.btnOpenCurrencyConverter).setOnClickListener {
            startActivity(Intent(this, CurrencyConverterActivity::class.java))
        }
    }
}