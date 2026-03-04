package com.example.gosleep.data

import com.example.gosleep.models.CalendarRepository
import com.example.gosleep.models.DaoRepository
import com.example.gosleep.models.SensorRepository

object Repositories {
    lateinit var sensorRepository: SensorRepository
    lateinit var daoRepository: DaoRepository
}