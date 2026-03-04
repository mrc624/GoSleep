package com.example.gosleep.models

import com.example.gosleep.data.GoSleepDao

class DaoRepository(private val dao: GoSleepDao) {

    val DEFAULT_SLEEP_HOURS: Float = 8.0f
    val DEFAULT_TIME_GET_READY: Float = 0.5f

    suspend fun updateOnPhone(time: Long) {
        dao.updateOnPhone(time)
    }

    suspend fun getOnPhone(): Long? {
        return dao.getOnPhone()
    }

    suspend fun getSleepHours(): Float {
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

    suspend fun updateSleepHours(hours: Float) {
        dao.updateSleepHours(hours)
    }

    suspend fun updateTimeGetReady(hours: Float) {
        dao.updateTimeGetReady(hours)
    }
}