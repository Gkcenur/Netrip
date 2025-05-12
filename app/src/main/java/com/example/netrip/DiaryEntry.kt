package com.example.netrip

data class DiaryEntry(
    val userId: String = "",
    val title: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
    val notes: String = "",
    val mood: String = "",
    val weather: String = "",
    val tripId: String = "",
    val photoUrls: List<String> = emptyList()
)
