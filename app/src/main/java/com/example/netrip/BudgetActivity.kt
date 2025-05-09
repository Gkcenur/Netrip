package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)

        // Geri butonu
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Toplam bütçe ve harcama
        val tvTotalBudget = findViewById<TextView>(R.id.tvTotalBudget)
        val tvTotalSpent = findViewById<TextView>(R.id.tvTotalSpent)
        val tvDays = findViewById<TextView>(R.id.tvDays)
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)

        // Örnek veriler
        val totalBudget = 2000.0
        val totalSpent = 1200.0
        val days = 10

        tvTotalBudget.text = "$%.2f".format(totalBudget)
        tvTotalSpent.text = "$%.0f".format(totalSpent)
        tvDays.text = "for $days days"
        progressBar.max = totalBudget.toInt()
        progressBar.progress = totalSpent.toInt()

        // Kategori listesi
        val rvBudgetCategories = findViewById<RecyclerView>(R.id.rvBudgetCategories)
        rvBudgetCategories.layoutManager = LinearLayoutManager(this)
        val categories = listOf(
            BudgetCategory("Accommodation", 750.0, R.drawable.bg_category_accommodation),
            BudgetCategory("Food", 250.0, R.drawable.bg_category_food),
            BudgetCategory("Transportation", 100.0, R.drawable.bg_category_transportation),
            BudgetCategory("Activities", 100.0, R.drawable.bg_category_activities)
        )
        rvBudgetCategories.adapter = BudgetCategoryAdapter(categories)

        // Kategorilerin toplamını hesapla
        val totalSpentCategories = categories.sumOf { it.amount }
        val tvTotalSpentCategories = findViewById<TextView>(R.id.tvTotalSpentCategories)
        tvTotalSpentCategories.text = "$%.0f".format(totalSpentCategories)

        // Son harcamalar listesi
        val rvExpenses = findViewById<RecyclerView>(R.id.rvExpenses)
        rvExpenses.layoutManager = LinearLayoutManager(this)
        val expenses = listOf(
            Expense(
                amount = 120.0,
                currency = "USD",
                category = "Food",
                date = "Today",
                time = "8:30 PM",
                location = "Le Jules Verne",
                notes = "",
                paymentMethod = "Credit Card"
            ),
            Expense(
                amount = 40.0,
                currency = "USD",
                category = "Attraction",
                date = "Today",
                time = "2:00 PM",
                location = "Eiffel Tower Tickets",
                notes = "",
                paymentMethod = "Credit Card"
            )
        )
        rvExpenses.adapter = ExpenseAdapter(expenses)

        // Artı butonu (yeni harcama ekle)
        val btnAdd = findViewById<ImageView>(R.id.btnAdd)
        val btnRemove = findViewById<ImageView>(R.id.btnRemove)

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddBudgetActivity::class.java)
            startActivity(intent)
        }

        btnRemove.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }
}
