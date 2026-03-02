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
        return _events.value.firstOrNull()
    }

}