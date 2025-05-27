package com.example.netrip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

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

    // Sabit döviz kurları tablosu
    private val rates = mapOf(
        "USD" to mapOf("EUR" to 0.91, "GBP" to 0.77, "TRY" to 32.25, "JPY" to 149.67, "CNY" to 7.24, "AUD" to 1.57, "CAD" to 1.43, "USD" to 1.0),
        "EUR" to mapOf("USD" to 1.09, "GBP" to 0.84, "TRY" to 35.24, "JPY" to 163.65, "CNY" to 7.91, "AUD" to 1.72, "CAD" to 1.56, "EUR" to 1.0),
        "GBP" to mapOf("USD" to 1.30, "EUR" to 1.19, "TRY" to 41.70, "JPY" to 194.37, "CNY" to 9.28, "AUD" to 2.04, "CAD" to 1.86, "GBP" to 1.0),
        "TRY" to mapOf("USD" to 0.031, "EUR" to 0.028, "GBP" to 0.024, "JPY" to 4.66, "CNY" to 0.22, "AUD" to 0.049, "CAD" to 0.044, "TRY" to 1.0),
        "JPY" to mapOf("USD" to 0.0067, "EUR" to 0.0061, "GBP" to 0.0051, "TRY" to 0.214, "CNY" to 0.048, "AUD" to 0.0105, "CAD" to 0.0095, "JPY" to 1.0),
        "CNY" to mapOf("USD" to 0.138, "EUR" to 0.126, "GBP" to 0.108, "TRY" to 20.65, "JPY" to 2.07, "AUD" to 0.22, "CAD" to 0.20, "CNY" to 1.0),
        "AUD" to mapOf("USD" to 0.64, "EUR" to 0.58, "GBP" to 0.49, "TRY" to 21.67, "JPY" to 95.35, "CNY" to 4.52, "CAD" to 0.91, "AUD" to 1.0),
        "CAD" to mapOf("USD" to 0.70, "EUR" to 0.64, "GBP" to 0.54, "TRY" to 22.33, "JPY" to 104.77, "CNY" to 5.02, "AUD" to 1.10, "CAD" to 1.0)
    )

    private val currencyList = listOf("USD", "EUR", "GBP", "TRY", "JPY", "CNY", "AUD", "CAD")

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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencyList)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Set up RecyclerView
        recentConversionAdapter = RecentConversionAdapter(recentConversions)
        rvRecentConversions.layoutManager = LinearLayoutManager(this)
        rvRecentConversions.adapter = recentConversionAdapter

        // Varsayılan seçimler
        spinnerFrom.setSelection(0) // USD
        spinnerTo.setSelection(1)   // EUR

        // Geri butonu
        btnBack.setOnClickListener { finish() }

        // Swap butonu
        btnSwap.setOnClickListener {
            val fromPos = spinnerFrom.selectedItemPosition
            val toPos = spinnerTo.selectedItemPosition
            spinnerFrom.setSelection(toPos)
            spinnerTo.setSelection(fromPos)
        }

        // Hesaplama fonksiyonu
        fun calculate() {
            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()
            val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val rate = rates[from]?.get(to) ?: 1.0
            val result = amount * rate
            tvConvertedAmount.text = String.format("%.2f", result)

            // Kur ve tarih bilgisini göster
            val sdf = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
            val now = sdf.format(Date())
            tvExchangeRate.text = "Exchange Rate: 1 $from = $rate $to\nLast updated: $now"

            // Add to recent conversions
            if (amount > 0) {
                val conversion = Conversion(from, to, amount, result, rate, "Today")
                recentConversions.add(0, conversion)
                if (recentConversions.size > 10) recentConversions.removeAt(recentConversions.size - 1)
                recentConversionAdapter.notifyDataSetChanged()
            }
        }

        // Değişiklikleri dinle
        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                calculate()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerTo.onItemSelectedListener = spinnerFrom.onItemSelectedListener

        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { calculate() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // İlk hesaplama
        calculate()
    }
}
