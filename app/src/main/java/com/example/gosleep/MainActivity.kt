package com.example.gosleep

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.gosleep.ui.theme.GoSleepTheme
import com.example.gosleep.ui.GoSleepScreen
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.viewmodels.GoSleepViewModelFactory
import com.example.gosleep.models.Repository
import kotlin.getValue


/**
 * The entry point activity that handles permissions and location triggers.
 *
 * @version 1.0
 */
class MainActivity : ComponentActivity() {

    /** Initialization of the ViewModel using the Manual DI container. */
    private val viewModel: GoSleepViewModel by viewModels {
        GoSleepViewModelFactory(Repository(applicationContext))
    }

    /** Launcher to handle location permission requests. */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fetchCalendarEvents()
        } else {
            Log.e("GoSleep", "READ_CALENDAR permission denied.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)

        setContent {
            GoSleepTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    GoSleepScreen(
                        viewModel = viewModel,
                        onRefresh = {
                            fetchCalendarEvents()
                        }
                    )
                }
            }
        }
    }

    /**
     * Reads upcoming calendar events and sends them to ViewModel
     */
    private fun fetchCalendarEvents() {
        viewModel.fetchCalendar()
    }
}