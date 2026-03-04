package com.example.gosleep.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gosleep.data.GoSleepDao
import com.example.gosleep.models.SensorRepository
import com.example.gosleep.models.CalendarRepository

class GoSleepViewModelFactory(private val calendarRepository: CalendarRepository, private val sensorRepository: SensorRepository, private val dao: GoSleepDao, private val context: Context): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoSleepViewModel::class.java)) {
            return GoSleepViewModel(
                calendarRepository = calendarRepository,
                sensorRepository = sensorRepository,
                dao = dao,
                context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}