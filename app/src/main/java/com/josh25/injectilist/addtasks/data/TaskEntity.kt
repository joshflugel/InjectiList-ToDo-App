package com.josh25.injectilist.addtasks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskEntity")
data class TaskEntity (
    @PrimaryKey
    val id: Int,
    val task:String,
    var selected:Boolean = false
)