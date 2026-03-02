package com.example.gosleep.viewmodels
import com.example.gosleep.models.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList
import com.example.gosleep.models.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class GoSleepViewModel(
    private val calendarRepository: CalendarRepository
): ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

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

}