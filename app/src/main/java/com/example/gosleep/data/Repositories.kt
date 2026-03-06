package com.example.gosleep.data

import android.content.Context
import androidx.room.Room
import com.example.gosleep.models.CalendarRepository
import com.example.gosleep.models.DaoRepository
import com.example.gosleep.models.SensorRepository
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


object Repositories {
    lateinit var sensorRepository: SensorRepository
    lateinit var daoRepository: DaoRepository
    private lateinit var database: GoSleepDatabase
}