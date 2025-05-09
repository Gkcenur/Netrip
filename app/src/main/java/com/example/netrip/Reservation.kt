package com.example.netrip

import java.io.Serializable

data class Reservation(
    val type: String,                // e.g., "Plane Flight", "Hotel", etc.
    val airline: String?,            // Only for flights
    val flightNumber: String?,       // Only for flights
    val from: String?,
    val to: String?,
    val departureDate: String?,
    val departureTime: String?,
    val arrivalDate: String?,
    val arrivalTime: String?,
    val confirmationNumber: String?  // Optional
) : Serializable
