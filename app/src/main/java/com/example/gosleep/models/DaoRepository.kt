package com.example.gosleep.models

import com.example.gosleep.data.GoSleepDao

class DaoRepository(private val dao: GoSleepDao) {

    suspend fun updateOnPhone(time: Long) {
        dao.updateOnPhone(time)
    }

    suspend fun getOnPhone(): Long? {
        return dao.getOnPhone()
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