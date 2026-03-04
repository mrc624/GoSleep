package com.example.gosleep.models

import android.app.Application
import android.content.Context
import android.provider.CalendarContract
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import com.example.gosleep.data.Event
import com.example.gosleep.data.GoSleepDao
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import com.example.gosleep.models.NotificationWorker

class CalendarRepository (
    private val context: Context,
    private val dao: GoSleepDao
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

    suspend fun getSleepHours(): Float? {
        return dao.getSleepHours()
    }

    suspend fun getTimeGetReady(): Float? {
        return dao.getTimeGetReady()
    }

    suspend fun updateSleepHours(hours: Float) {
        dao.updateSleepHours(hours)
    }

    suspend fun updateTimeGetReady(hours: Float) {
        dao.updateTimeGetReady(hours)
    }
}