package com.example.netrip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSave: Button
    private lateinit var etAmount: EditText
    private lateinit var tvCurrency: TextView
    private lateinit var categoryButtons: List<ToggleButton>
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var etLocation: EditText
    private lateinit var etNotes: EditText
    private lateinit var etPaymentMethod: EditText

    private val categories = listOf("Food", "Transport", "Activities", "Shopping")
    private var selectedCategory: String? = null

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        etAmount = findViewById(R.id.etAmount)
        tvCurrency = findViewById(R.id.tvCurrency)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        etLocation = findViewById(R.id.etLocation)
        etNotes = findViewById(R.id.etNotes)
        etPaymentMethod = findViewById(R.id.etPaymentMethod)

        // Category buttons
        categoryButtons = listOf(
            findViewById(R.id.btnCategoryFood),
            findViewById(R.id.btnCategoryTransport),
            findViewById(R.id.btnCategoryActivities),
            findViewById(R.id.btnCategoryShopping)
        )

        // Set up category selection
        categoryButtons.forEachIndexed { index, button ->
            button.text = categories[index]
            button.textOn = categories[index]
            button.textOff = categories[index]
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedCategory = categories[index]
                    // Uncheck others and reset their style
                    categoryButtons.forEachIndexed { i, btn ->
                        if (i != index) {
                            btn.isChecked = false
                            btn.setBackgroundResource(R.drawable.bg_card)
                            btn.setTextColor(resources.getColor(R.color.diaryPeach, null))
                        }
                    }
                    // Set active style
                    button.setBackgroundResource(R.drawable.bg_card_active)
                    button.setTextColor(resources.getColor(android.R.color.black, null))
                } else {
                    button.setBackgroundResource(R.drawable.bg_card)
                    button.setTextColor(resources.getColor(R.color.diaryPeach, null))
                }
            }
        }

        // Date picker
        etDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    etDate.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Time picker
        etTime.setOnClickListener {
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    etTime.setText(timeFormat.format(calendar.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        val currencies = arrayOf("USD", "EUR", "GBP", "TRY", "JPY", "CNY", "AUD", "CAD")
        tvCurrency.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Select Currency")
                .setItems(currencies) { _, which ->
                    tvCurrency.text = currencies[which]
                }
                .show()
        }

        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveExpense() }
    }

    private fun saveExpense() {
        // val amount = etAmount.text.toString()
        // val currency = tvCurrency.text.toString()
        // val category = selectedCategory
        // val date = etDate.text.toString()
        // val time = etTime.text.toString()
        // val location = etLocation.text.toString()
        // val notes = etNotes.text.toString()
        // val paymentMethod = etPaymentMethod.text.toString()

        Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
