package com.example.gosleep.data

import java.time.LocalDateTime

data class Event(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)