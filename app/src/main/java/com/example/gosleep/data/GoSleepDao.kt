package com.example.gosleep.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

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

    /**
     * Inserts or updates the user's sleep configuration.
     *
     * @param record The new GoSleepRecord to persist.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: GoSleepRecord)
}