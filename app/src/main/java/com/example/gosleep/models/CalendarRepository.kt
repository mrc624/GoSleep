package com.example.gosleep.models
import android.content.Context
import android.provider.CalendarContract
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class CalendarRepository (
    private val context: Context
){

    fun getEvents(nextHours: Int): List<Event>
    {
        val events = mutableListOf<Event>()

        val start = System.currentTimeMillis()
        val end = start + nextHours * 60 * 60 * 1000L

        val projection = arrayOf(
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
        )

        val selection = """
            ${CalendarContract.Events.DTSTART} < ? AND 
            ${CalendarContract.Events.DTEND} > ? AND
            ${CalendarContract.Events.DELETED} = 0
        """.trimIndent()

        val selectionArgs = arrayOf(
            end.toString(),
            start.toString()
        )

        val cursor = context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            CalendarContract.Events.DTSTART + " ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(0) ?: "No Title"

                val startTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it.getLong(1)),
                    ZoneId.systemDefault()
                )

                val endTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it.getLong(2)),
                    ZoneId.systemDefault()
                )

                events.add(Event(name, startTime, endTime))
            }
        }

        return events
    }
}