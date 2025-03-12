package com.josh25.injectilist.addtasks.domain

import com.josh25.injectilist.addtasks.data.TaskRepository
import com.josh25.injectilist.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// 147)
class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> {
        return taskRepository.tasks
    }
}