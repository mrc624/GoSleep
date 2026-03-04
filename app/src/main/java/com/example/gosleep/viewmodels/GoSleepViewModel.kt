package com.example.gosleep.viewmodels
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class GoSleepViewModel(
    private val calendarRepository: CalendarRepository,
    private val sensorRepository: SensorRepository,
    private val dao: GoSleepDao,
    private val context: Context
): ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun sendNotificationIfEventSoon(nextEvent: Event) {
        viewModelScope.launch {
            val currentTime = LocalDateTime.now()
            val duration = Duration.between(currentTime, nextEvent.startTime)

            // 6 hours in milliseconds
            val sixHoursMillis = 6 * 60 * 60 * 1000L

            if (duration.toMillis() in 1..sixHoursMillis) {
                // Create notification channel (required for Android O+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        "gosleep_channel",
                        "GoSleep Notifications",
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        description = "Notifications for upcoming events"
                    }
                    val notificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                // Build the notification
                val notification = NotificationCompat.Builder(context, "gosleep_channel")
                    .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your icon
                    .setContentTitle("GoSleep Reminder")
                    .setContentText("Your next event is in less than 6 hours!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()

                // Show the notification
                try {
                    NotificationManagerCompat.from(context).notify(System.currentTimeMillis().toInt(), notification)
                } catch (e: SecurityException) {
                    // Permission not granted, maybe log or ignore
                    e.printStackTrace()
                }
            }
        }
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

    fun toggleNotifications(state: Boolean)
    {

    }

    fun getNotifications(): Boolean
    {
        return true
    }

    fun submitPrefSleepTime(time: Float)
    {

    }

    fun getPrefSleepTime(): Float
    {
        return 8.0f
    }

    fun submitReadyTime(time: Float)
    {

    }

    fun getReadyTime(): Float
    {
        return 1.0f
    }

}