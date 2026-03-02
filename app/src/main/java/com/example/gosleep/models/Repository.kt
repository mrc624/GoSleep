package com.example.gosleep.models
import android.content.Context
import android.provider.CalendarContract
import android.util.Log
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class Repository (
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

        /*cursor?.use {
            Log.d("CalendarDebug", "Total events: ${it.count}")
            while (it.moveToNext()) {
                val title = it.getString(0)
                val start = it.getLong(1)
                val end = it.getLong(2)
                Log.d("CalendarDebug", "Event: $title, start=$start, end=$end")
            }
        }*/

        return events
    }
}