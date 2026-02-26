package com.example.gosleep.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gosleep.data.GoSleepDao
import com.example.gosleep.data.SensorRepository
import com.example.gosleep.models.CalendarRepository

class GoSleepViewModelFactory(private val calendarRepository: CalendarRepository, private val sensorRepository: SensorRepository, private val dao: GoSleepDao): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoSleepViewModel::class.java)) {
            return GoSleepViewModel(calendarRepository, sensorRepository, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}