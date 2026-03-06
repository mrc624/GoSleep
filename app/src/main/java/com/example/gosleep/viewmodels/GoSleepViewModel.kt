package com.example.gosleep.viewmodels
import android.annotation.SuppressLint
import android.content.Context
import com.example.gosleep.data.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gosleep.data.GoSleepDao
import com.example.gosleep.models.SensorRepository
import com.example.gosleep.models.CalendarRepository
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.gosleep.data.Repositories
import com.example.gosleep.models.DaoRepository
import com.example.gosleep.models.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class GoSleepViewModel(
    private val calendarRepository: CalendarRepository,
    private val sensorRepository: SensorRepository,
    private val daoRepository: DaoRepository,
    private val context: Context
): ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _sleepHours = MutableStateFlow(8.0f)
    val sleepHours: StateFlow<Float> = _sleepHours

    private val _readyTime = MutableStateFlow(0.5f)
    val readyTime: StateFlow<Float> = _readyTime

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _notificationsStart = MutableStateFlow(LocalTime.of(22, 0))
    val notificationsStart: StateFlow<LocalTime> = _notificationsStart

    private val _notificationsEnd = MutableStateFlow(LocalTime.of(7, 0))
    val notificationsEnd: StateFlow<LocalTime> = _notificationsEnd


    init {
        Repositories.sensorRepository = sensorRepository
        Repositories.daoRepository = daoRepository

        viewModelScope.launch(Dispatchers.IO) {
            sensorRepository.detectPhoneUsage().collect()
        }

        viewModelScope.launch(Dispatchers.IO) {
            _sleepHours.value = daoRepository.getSleepHours()
            _readyTime.value = daoRepository.getTimeGetReady()
            _notificationsEnabled.value = daoRepository.getNotifications()
            _notificationsStart.value = daoRepository.getNotificationsStart()
            _notificationsEnd.value = daoRepository.getNotificationsEnd()
        }

        fetchCalendar()
        scheduleEventNotifications()
    }

    private fun scheduleEventNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
            "event_notifications",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun fetchCalendar(){
        viewModelScope.launch (Dispatchers.IO){
            _isLoading.value = true
            val nextEvents = calendarRepository.getEvents(48)
            _events.value = nextEvents
            _isLoading.value = false
        }
    }

    fun getNextEvent(): Event?{
        fetchCalendar()
        for (event in _events.value)
        {
            if (event.startTime >= LocalDateTime.now())
            {
                return event
            }
        }
        return null
    }

    fun getFirstMorningEvent(morningStart: LocalDateTime): Event?{
        fetchCalendar()
        for (event in _events.value)
        {
            if (event.startTime >= morningStart)
            {
                return event
            }
        }
        return null
    }

    fun getSleepHours(event: Event?): Float
    {
        if (event != null)
        {
            val duration = Duration.between(LocalDateTime.now(), event.startTime)

            val hours: Float = (duration.toMinutes() / 60.0).toFloat()

            if (hours > 0)
            {
                return hours
            }
        }
        return 0.0f
    }

    fun getMorningStart(): LocalDateTime
    {
        val now = LocalDateTime.now()

        var nextSixAM = now
            .withHour(6)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        while (!nextSixAM.isAfter(now)) {
            nextSixAM = nextSixAM.plusDays(1)
        }

        return nextSixAM
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateNotifications(enabled)
            _notificationsEnabled.value = enabled
        }
    }

    fun submitPrefSleepTime(time: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateSleepHours(time)
            _sleepHours.value = time
        }
    }

    fun submitReadyTime(time: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateTimeGetReady(time)
            _readyTime.value = time
        }
    }

    fun submitNotificationsStart(time: LocalTime) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateNotificationsStart(time)
            _notificationsStart.value = time
        }
    }

    fun submitNotificationsEnd(time: LocalTime) {
        viewModelScope.launch(Dispatchers.IO) {
            daoRepository.updateNotificationsEnd(time)
            _notificationsEnd.value = time
        }
    }

}