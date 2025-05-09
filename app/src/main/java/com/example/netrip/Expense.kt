package com.example.netrip

import java.io.Serializable

data class Expense(
    val amount: Double,
    val currency: String,
    val category: String,
    val date: String,         // You can use Date type if you prefer, but String is easier for passing between activities
    val time: String,
    val location: String,
    val notes: String,
    val paymentMethod: String
) : Serializable
