package com.example.gosleep.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gosleep.data.GoSleepDao
import com.example.gosleep.models.SensorRepository
import com.example.gosleep.models.CalendarRepository
import com.example.gosleep.models.DaoRepository

class GoSleepViewModelFactory(private val calendarRepository: CalendarRepository, private val sensorRepository: SensorRepository, private val daoRepository: DaoRepository, private val context: Context): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoSleepViewModel::class.java)) {
            return GoSleepViewModel(
                calendarRepository = calendarRepository,
                sensorRepository = sensorRepository,
                daoRepository = daoRepository,
                context = context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}