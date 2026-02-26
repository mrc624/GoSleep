package com.example.gosleep.models

import java.time.LocalDateTime

data class Event(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)