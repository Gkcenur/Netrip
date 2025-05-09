package com.example.netrip

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSwap: ImageButton
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var etAmount: EditText
    private lateinit var tvConvertedAmount: TextView
    private lateinit var tvExchangeRate: TextView
    private lateinit var tvLastUpdated: TextView
    private lateinit var rvRecentConversions: RecyclerView

    private lateinit var recentConversionAdapter: RecentConversionAdapter
    private val recentConversions = mutableListOf<Conversion>()

    private val currencies = arrayOf("USD", "EUR", "GBP", "TRY", "JPY", "CNY", "AUD", "CAD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        btnBack = findViewById(R.id.btnBack)
        btnSwap = findViewById(R.id.btnSwap)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        etAmount = findViewById(R.id.etAmount)
        tvConvertedAmount = findViewById(R.id.tvConvertedAmount)
        tvExchangeRate = findViewById(R.id.tvExchangeRate)
        tvLastUpdated = findViewById(R.id.tvLastUpdated)
        rvRecentConversions = findViewById(R.id.rvRecentConversions)

        // Set up spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Set up RecyclerView
        recentConversionAdapter = RecentConversionAdapter(recentConversions)
        rvRecentConversions.layoutManager = LinearLayoutManager(this)
        rvRecentConversions.adapter = recentConversionAdapter

        // Swap currencies
        btnSwap.setOnClickListener {
            val fromPos = spinnerFrom.selectedItemPosition
            val toPos = spinnerTo.selectedItemPosition
            spinnerFrom.setSelection(toPos)
            spinnerTo.setSelection(fromPos)
            convertCurrency()
        }

        // Convert when amount or currency changes
        etAmount.setOnEditorActionListener { _, _, _ ->
            convertCurrency()
            true
        }
        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun convertCurrency() {
        val from = spinnerFrom.selectedItem.toString()
        val to = spinnerTo.selectedItem.toString()
        val amountStr = etAmount.text.toString()
        val amount = amountStr.toDoubleOrNull() ?: 0.0

        // TODO: Replace with real exchange rate logic
        val rate = getMockExchangeRate(from, to)
        val converted = amount * rate

        tvConvertedAmount.text = String.format("%.2f", converted)
        tvExchangeRate.text = "Exchange Rate: 1 $from = $rate $to"
        tvLastUpdated.text = "Last updated: May 18, 2025, 09:41 AM"

        // Add to recent conversions
        if (amount > 0) {
            val conversion = Conversion(from, to, amount, converted, rate, "Today")
            recentConversions.add(0, conversion)
            if (recentConversions.size > 10) recentConversions.removeAt(recentConversions.size - 1)
            recentConversionAdapter.notifyDataSetChanged()
        }
    }

    // Mock exchange rates for demonstration
    private fun getMockExchangeRate(from: String, to: String): Double {
        return when (from to to) {
            "USD" to "EUR" -> 0.9234
            "EUR" to "USD" -> 1.0829
            "USD" to "GBP" -> 0.79
            "GBP" to "USD" -> 1.27
            else -> 1.0 // Default for same currency or unknown
        }
    }
}
