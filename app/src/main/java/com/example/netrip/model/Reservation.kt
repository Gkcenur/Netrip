package com.example.netrip.model

enum class ReservationType { FLIGHT, HOTEL, ACTIVITY }

data class Reservation(
    val id: String,
    val type: ReservationType,
    val title: String,
    val subtitle: String,
    val dateInfo: String,
    val location: String,
    val status: String, // "Confirmed" vs.
    val initials: String // "AF", "H", "ET" gibi
)
