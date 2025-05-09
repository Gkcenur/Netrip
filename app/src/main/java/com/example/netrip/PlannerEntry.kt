package com.example.netrip

data class PlannerEntry(
    val title: String,
    val time: String,
    val description: String,
    val typeIcon: Int,
    val isFavorite: Boolean
)
