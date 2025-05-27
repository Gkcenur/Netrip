package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BudgetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView ve supportActionBar?.hide() kaldırıldı, BaseActivity hallediyor
        // Diğer UI işlemleri aşağıda

        // Geri butonu
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Total Budget kartı
        val tvTotalBudget = findViewById<TextView>(R.id.tvTotalBudget)
        val tvTotalSpent = findViewById<TextView>(R.id.tvTotalSpent)
        val tvDays = findViewById<TextView>(R.id.tvDays)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnAddBudget = findViewById<ImageView>(R.id.btnAddBudget)

        // Expense Categories kartı
        val rvBudgetCategories = findViewById<RecyclerView>(R.id.rvBudgetCategories)
        val tvTotalSpentCategories = findViewById<TextView>(R.id.tvTotalSpentCategories)
        val btnAddExpense = findViewById<ImageView>(R.id.btnAddExpense)

        // Recent Expenses
        val rvExpenses = findViewById<RecyclerView>(R.id.rvExpenses)

        // Örnek veriler
        val totalBudget = 2000.0
        val totalSpent = 1200.0
        val days = 10

        tvTotalBudget.text = "$%.2f".format(totalBudget)
        tvTotalSpent.text = "$%.0f spent".format(totalSpent)
        tvDays.text = "for $days days"
        progressBar.max = totalBudget.toInt()
        progressBar.progress = totalSpent.toInt()

        val categories = listOf(
            BudgetCategory("Accommodation", 750.0, R.drawable.bg_category_accommodation),
            BudgetCategory("Food", 250.0, R.drawable.bg_category_food),
            BudgetCategory("Transportation", 100.0, R.drawable.bg_category_transportation),
            BudgetCategory("Activities", 100.0, R.drawable.bg_category_activities)
        )
        rvBudgetCategories.layoutManager = LinearLayoutManager(this)
        rvBudgetCategories.adapter = BudgetCategoryAdapter(categories)
        val totalSpentCategories = categories.sumOf { it.amount }
        tvTotalSpentCategories.text = "$%.0f".format(totalSpentCategories)

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
        rvExpenses.layoutManager = LinearLayoutManager(this)
        rvExpenses.adapter = ExpenseAdapter(expenses)

        // Add Budget butonu
        btnAddBudget.setOnClickListener {
            val intent = Intent(this, AddBudgetActivity::class.java)
            startActivity(intent)
        }

        // Add Expense butonu
        btnAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_budget
}
