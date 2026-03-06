package com.example.gosleep.data

import android.content.Context
import androidx.room.Room
import com.example.gosleep.models.CalendarRepository
import com.example.gosleep.models.DaoRepository
import com.example.gosleep.models.SensorRepository
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


object Repositories {
    lateinit var sensorRepository: SensorRepository
    lateinit var daoRepository: DaoRepository

    private lateinit var database: GoSleepDatabase
    fun initialize(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
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

            daoRepository = DaoRepository(database.goSleepDao())

    }


}