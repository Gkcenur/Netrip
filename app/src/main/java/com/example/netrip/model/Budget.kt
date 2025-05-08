package com.example.netrip.model

// Data class representing a travel budget

data class Budget(
    val tripPlace: String,
    val tripDate: String,
    val totalBudget: Double,
    val currency: String,
    val duration: Int,
    val durationType: String,
    val categories: List<Category>
)
