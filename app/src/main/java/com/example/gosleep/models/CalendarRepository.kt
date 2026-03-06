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
import com.example.gosleep.data.Repositories
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit
import com.example.gosleep.models.NotificationWorker
import java.time.Duration
import java.time.LocalTime

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

    suspend fun getSleepHours(): Float {
        return Repositories.daoRepository.getSleepHours()
    }

    suspend fun getTimeGetReady(): Float {
        return Repositories.daoRepository.getTimeGetReady()
    }

    suspend fun updateSleepHours(hours: Float) {
        Repositories.daoRepository.updateSleepHours(hours)
    }

    suspend fun updateTimeGetReady(hours: Float) {
        Repositories.daoRepository.updateTimeGetReady(hours)
    }

    fun isWithinEventHours(time: LocalTime, notificationsStart: LocalTime, notificationsEnd: LocalTime): Boolean {
        return if (notificationsStart <= notificationsEnd) {
            // Normal range (e.g., 09:00 → 17:00)
            time.isAfter(notificationsStart) && time.isBefore(notificationsEnd)
        } else {
            // Overnight range (e.g., 22:00 → 07:00)
            time.isAfter(notificationsStart) || time.isBefore(notificationsEnd)
        }
    }

    fun getNextEvent(): Event? {
        val events = getEvents(48)
        val nextEvent = events.firstOrNull { it.startTime >= LocalDateTime.now() }
        return nextEvent
    }

    fun getFirstWakeupEvent(): Event? {
        val events = getEvents(48)

        val notificationsStart: LocalTime = Repositories.daoRepository.getNotificationsStart()
        val notificationsEnd: LocalTime = Repositories.daoRepository.getNotificationsEnd()

        val nextWakeupEvent = events.firstOrNull { event ->
            val eventTime = event.startTime.toLocalTime()
            event.startTime >= LocalDateTime.now() && isWithinEventHours(eventTime, notificationsStart, notificationsEnd)
        }

        return nextWakeupEvent
    }

    suspend fun isWithinWakeup(): Boolean {
        val minutes = (Repositories.daoRepository.getTimeGetReady() * 60).toLong()
        val duration = Duration.ofMinutes(minutes)
        val nextWakeupEvent = getFirstWakeupEvent()

        val wakeUp = nextWakeupEvent?.startTime?.minus(duration)
        val now = LocalDateTime.now()

        // true if they are awake
        return now >= wakeUp
    }
}