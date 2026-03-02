package com.example.gosleep.viewmodels
import com.example.gosleep.models.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList
import com.example.gosleep.models.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class GoSleepViewModel(
    private val repository: Repository
): ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCalendar(){
        viewModelScope.launch (Dispatchers.IO){
            _isLoading.value = true
            val nextEvents = repository.getEvents(48)
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

}