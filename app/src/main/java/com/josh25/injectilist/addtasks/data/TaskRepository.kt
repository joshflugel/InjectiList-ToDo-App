package com.josh25.injectilist.addtasks.data

import com.josh25.injectilist.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao){

    // Not this, it's not CLEAN to expose TaskEntity from the Data Layer to Domain and UI Layers
    // val tasks: Flow<List<TaskModel>> = taskDao.getTasks()
    // ...use a Mapper instead, a transform of an object from one Layer, adapted to a different Layer
    // TaskModel  <-- Mapper <-- TakEntity

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.selected) } }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }
}