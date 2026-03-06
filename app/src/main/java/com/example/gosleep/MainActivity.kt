package com.example.gosleep

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.activity.viewModels
import com.example.gosleep.ui.theme.GoSleepTheme
import com.example.gosleep.ui.GoSleepScreen
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.viewmodels.GoSleepViewModelFactory
import com.example.gosleep.models.CalendarRepository
import kotlin.getValue
import androidx.room.Room
import com.example.gosleep.data.GoSleepDatabase
import com.example.gosleep.models.SensorRepository
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gosleep.data.Repositories.daoRepository
import com.example.gosleep.models.DaoRepository


/**
 * The entry point activity that handles permissions and location triggers.
 *
 * @version 1.0
 */
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: GoSleepViewModel

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

        // Build database safely
        val db = Room.databaseBuilder(
            applicationContext,
            GoSleepDatabase::class.java,
            "gosleep.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    // Prepopulate the settings row so the table is never empty
                    db.execSQL(
                        """
                        INSERT INTO goSleep_table(id, sleepHours, timeGetReady, onPhone, notifications, notificationsStart, notificationsEnd)
                        VALUES (1, 8.0, 0.5, 0, 1, 18000, 43200)
                    """.trimIndent()
                    )
                }
            })
            .fallbackToDestructiveMigration(false)
            .build()

        val dao = db.goSleepDao()

        // Get sensor manager safely
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorRepository = SensorRepository(sensorManager)
        val daoRepository = DaoRepository(dao)

        // Initialize ViewModel properly
        viewModel = ViewModelProvider(
            this,
            GoSleepViewModelFactory(
                calendarRepository = CalendarRepository(applicationContext),
                sensorRepository = sensorRepository,
                daoRepository = daoRepository,
                applicationContext
            )
        )[GoSleepViewModel::class.java]

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