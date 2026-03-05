package com.example.gosleep.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * This class stores a single persisted shake count. By using Room,
 * this survives the Android Low Memory Killer (LMK).
 *
 * @property id A key for this record
 * @property sleepHours The desired number of hours for the user to sleep
 * @property timeGetReady The time needed for the user to get ready in the morning
 * @property onPhone Logs the time a user goes on their phone
 * @property notifications True if notifcations are turned on, false uf they are off,
 * @property notificationsStart Logs when notifications should start for the day
 * @property notificationsEnd Logs when notifications should end for the day
 *
 * @author Molly Olsen and Matthew Cloutier
 * @version 1.0
 */

@Entity(tableName = "goSleep_table")
data class GoSleepRecord (
    @PrimaryKey val id: Int = 1,

    val sleepHours: Float,

    val timeGetReady: Float,

    val onPhone: Long,

    val notifications: Boolean,

    val notificationsStart: Long,

    val notificationsEnd: Long
)
