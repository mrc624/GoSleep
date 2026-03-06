package com.example.gosleep.data
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * This class serves as the main access point for
 * the persistent relational data.
 *
 * @author Molly Olsen and Matthew Cloutier
 * @version 1.0
 */

@Database(entities = [GoSleepRecord::class], version = 5, exportSchema = false)
abstract class GoSleepDatabase : RoomDatabase() {

    /**
     * Connects the Database to the Data Access Object (DAO).
     *
     * @return A [GoSleepDao]
     */
    abstract fun goSleepDao(): GoSleepDao
}