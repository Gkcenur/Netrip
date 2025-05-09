package com.example.netrip

data class DiaryEntry(
    val place: String,
    val date: String,
    val description: String,
    val time: String,
    val weatherRes: Int,
    val moodRes: Int,
    val likeRes: Int,
    val photoBgRes: Int,
    val headerBgRes: Int
)
