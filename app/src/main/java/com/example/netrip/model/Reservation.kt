package com.example.netrip.model

enum class ReservationType { FLIGHT, HOTEL, ACTIVITY }

data class Reservation(
    val id: String = "",
    val type: ReservationType = ReservationType.FLIGHT,
    val title: String = "",
    val subtitle: String = "",
    val date: String = "",
    val location: String = "",
    val status: String = "",
    val initials: String = ""
)
