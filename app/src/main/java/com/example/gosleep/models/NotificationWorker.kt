package com.example.gosleep.models

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.activity.compose.ReportDrawn
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.gosleep.data.Repositories
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime


class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {


    private val calendarRepository = CalendarRepository(appContext)
    private val sensorRepository = Repositories.sensorRepository
    private val daoRepository = Repositories.daoRepository

    override suspend fun doWork(): Result {

        val nextWakeupEvent = calendarRepository.getFirstWakeupEvent()

        val sleep = daoRepository.getSleepHours()

        nextWakeupEvent?.let {
            val duration = Duration.between(LocalDateTime.now(), it.startTime)

            if (sensorRepository.isUserAwake() && !calendarRepository.isWithinWakeup()) {
                val context = applicationContext

                val channel = NotificationChannel(
                    "gosleep_channel",
                    "GoSleep Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply { description = "Notifications for upcoming events" }
                val manager = context.getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)

                val notification = NotificationCompat.Builder(context, "gosleep_channel")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("GoSleep Reminder")
                    .setContentText("Your next event is in less than $sleep hours!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()

                try {
                    NotificationManagerCompat.from(context)
                        .notify(System.currentTimeMillis().toInt(), notification)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }

        return Result.success()
    }
}