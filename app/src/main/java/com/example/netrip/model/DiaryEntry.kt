package com.example.netrip.model

import android.net.Uri

// Data class representing a diary entry

data class DiaryEntry(
    val title: String,
    val place: String,
    val date: String,
    val time: String,
    val photos: List<Uri>,
    val notes: String,
    val weather: String,
    val mood: String
)
