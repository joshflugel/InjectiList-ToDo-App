package com.josh25.injectilist.addtasks.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    // DAO
    abstract fun taskDao():TaskDao
}