package com.josh25.injectilist.ui.model

data class TaskModel(val id:Long = System.currentTimeMillis(), val task:String, var selected:Boolean = false)
