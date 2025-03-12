package com.josh25.injectilist.ui.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(), // hashCode is also unique
    val task: String,
    var selected: Boolean = false
)
