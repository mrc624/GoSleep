package com.example.gosleep.models

import android.util.Log
import com.example.gosleep.data.GoSleepDao
import java.time.LocalDateTime
import java.time.LocalTime

class DaoRepository(private val dao: GoSleepDao) {

    val DEFAULT_SLEEP_HOURS: Float = 8.0f
    val DEFAULT_TIME_GET_READY: Float = 0.5f
    val DEFAULT_NOTIFICATIONS_START: LocalTime = LocalTime.of(22, 0)
    val DEFAULT_NOTIFICATIONS_END: LocalTime = LocalTime.of(7,0)

    fun LocalTime.toLong(): Long = this.toSecondOfDay().toLong()

    fun Long.toLocalTime(): LocalTime = LocalTime.ofSecondOfDay(this)

    suspend fun updateOnPhone(time: Long) {
        dao.updateOnPhone(time)
    }

    suspend fun getOnPhone(): Long? {
        return dao.getOnPhone()
    }

    suspend fun getSleepHours(): Float {
        Log.d("DB", "Got sleep")
        val time: Float? = dao.getSleepHours()
        if (time == null)
        {
            updateSleepHours(DEFAULT_SLEEP_HOURS)
            return DEFAULT_SLEEP_HOURS
        }
        else
        {
            return time
        }
    }

    suspend fun getTimeGetReady(): Float {
        val time: Float? = dao.getTimeGetReady()
        if (time == null)
        {
            updateTimeGetReady(DEFAULT_TIME_GET_READY)
            return DEFAULT_TIME_GET_READY
        }
        else
        {
            return time
        }
    }

    suspend fun getNotificationsStart(): LocalTime {
        val stored = dao.getNotificationsStart()
        return if (stored == null) {
            val defaultLong = DEFAULT_NOTIFICATIONS_START.toLong()
            dao.updateNotificationsStart(defaultLong)
            DEFAULT_NOTIFICATIONS_START
        } else {
            stored.toLocalTime()
        }
    }

    suspend fun getNotificationsEnd(): LocalTime {
        val stored = dao.getNotificationsEnd()
        return if (stored == null) {
            val defaultLong = DEFAULT_NOTIFICATIONS_END.toLong()
            dao.updateNotificationsEnd(defaultLong)
            DEFAULT_NOTIFICATIONS_END
        } else {
            stored.toLocalTime()
        }
    }

    fun getNotifications(): Boolean {
        val notif: Boolean? =  dao.getNotifications()

        if (notif == null)
        {
            updateNotifications(true)
            return true
        }
        else
        {
            return notif
        }
    }

    suspend fun updateSleepHours(hours: Float) {
        dao.updateSleepHours(hours)
    }

    suspend fun updateTimeGetReady(hours: Float) {
        dao.updateTimeGetReady(hours)
    }

    fun updateNotifications(notifications: Boolean) {
        dao.updateNotifications(notifications)
    }

    suspend fun updateNotificationsStart(time: LocalTime) {
        dao.updateNotificationsStart(time.toLong())
    }

    suspend fun updateNotificationsEnd(time: LocalTime) {
        dao.updateNotificationsEnd(time.toLong())
    }
}