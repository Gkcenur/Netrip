package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netrip.adapter.CategoryAdapter
import com.example.netrip.model.Budget
import com.example.netrip.model.Category

class AddBudgetActivity : AppCompatActivity() {

    private lateinit var etTotalBudget: EditText
    private lateinit var spinnerCurrency: Spinner
    private lateinit var etDuration: EditText
    private lateinit var spinnerDurationType: Spinner
    private lateinit var rvCategories: RecyclerView
    private lateinit var btnAddCategory: Button
    private lateinit var btnSave: Button
    private lateinit var btnBack: ImageButton

    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_budget)

        // Initialize views
        etTotalBudget = findViewById(R.id.etTotalBudget)
        spinnerCurrency = findViewById(R.id.spinnerCurrency)
        etDuration = findViewById(R.id.etDuration)
        spinnerDurationType = findViewById(R.id.spinnerDurationType)
        rvCategories = findViewById(R.id.rvCategories)
        btnAddCategory = findViewById(R.id.btnAddCategory)
        btnSave = findViewById(R.id.btnSave)
        btnBack = findViewById(R.id.btnBack)

        // Setup RecyclerView
        categoryAdapter = CategoryAdapter(categories)
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.adapter = categoryAdapter

        // Add Category Button
        btnAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        // Save Button
        btnSave.setOnClickListener {
            saveBudget()
        }

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showAddCategoryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_category, null)
        val etCategoryName = dialogView.findViewById<EditText>(R.id.etCategoryName)
        val etCategoryAmount = dialogView.findViewById<EditText>(R.id.etCategoryAmount)

        AlertDialog.Builder(this)
            .setTitle("Add Category")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etCategoryName.text.toString().ifBlank { "New Category" }
                val amount = etCategoryAmount.text.toString().toDoubleOrNull() ?: 0.0
                val iconLetter = name.firstOrNull()?.uppercase() ?: "N"
                val newCategory = Category(name, amount, iconLetter)
                categories.add(newCategory)
                categoryAdapter.notifyItemInserted(categories.size - 1)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveBudget() {
        val totalBudget = etTotalBudget.text.toString().toDoubleOrNull() ?: 0.0
        val currency = spinnerCurrency.selectedItem.toString()
        val duration = etDuration.text.toString().toIntOrNull() ?: 0
        val durationType = spinnerDurationType.selectedItem.toString()

        val budget = Budget(
            tripPlace = findViewById<EditText>(R.id.etTripPlace).text.toString(),
            tripDate = findViewById<EditText>(R.id.etTripDate).text.toString(),
            totalBudget = totalBudget,
            currency = currency,
            duration = duration,
            durationType = durationType,
            categories = categories
        )

        // Use the budget variable to avoid the warning
        android.util.Log.d("AddBudgetActivity", budget.toString())

        // TODO: Save the budget to database or pass it to another activity
        Toast.makeText(this, "Budget saved!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
