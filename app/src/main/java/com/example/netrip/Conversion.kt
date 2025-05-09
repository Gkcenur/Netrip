package com.example.netrip

import java.io.Serializable

data class Conversion(
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: Double,
    val toAmount: Double,
    val rate: Double,
    val date: String // e.g., "Today", "Yesterday", or a formatted date string
) : Serializable
