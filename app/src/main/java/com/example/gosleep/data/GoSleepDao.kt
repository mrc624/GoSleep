package com.example.gosleep.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * This interface contains the data-access object implementation.
 *
 * @author Molly Olsen and Matthew Cloutier
 * @version 1.0
 */

@Dao
interface GoSleepDao {

    /**
     * Returns the single GoSleepRecord stored in the database.
     *
     * @return A Flow emitting the record, or null if not yet created.
     */

    @Query("SELECT * FROM goSleep_table WHERE id = 1")
    fun getRecordFlow(): Flow<GoSleepRecord?>

    @Query("SELECT sleepHours FROM goSleep_table WHERE id = 1")
    fun getSleepHours(): Float?

    @Query("SELECT timeGetReady FROM goSleep_table WHERE id = 1")
    fun getTimeGetReady(): Float?

    @Query("SELECT notifications FROM goSleep_table WHERE id = 1")
    fun getNotifications(): Boolean?

    @Query("SELECT onPhone FROM goSleep_table WHERE id = 1")
    suspend fun getOnPhone(): Long?

    /**
     * Inserts or updates the user's sleep configuration.
     *
     * @param record The new GoSleepRecord to persist.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: GoSleepRecord)

    @Update
    suspend fun updateRecord(record: GoSleepRecord)

    @Query("UPDATE goSleep_table SET sleepHours = :hours WHERE id = 1")
    suspend fun updateSleepHours(hours: Float)

    @Query("UPDATE goSleep_table SET timeGetReady = :hours WHERE id = 1")
    suspend fun updateTimeGetReady(hours: Float)

    @Query("UPDATE goSleep_table SET onPhone = :time WHERE id = 1")
    suspend fun updateOnPhone(time: Long)

    @Query("UPDATE goSleep_table SET notifications = :notifications WHERE id = 1")
    fun updateNotifications(notifications: Boolean)
}