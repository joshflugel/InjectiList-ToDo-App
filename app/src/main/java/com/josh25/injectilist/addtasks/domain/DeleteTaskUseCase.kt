package com.josh25.injectilist.addtasks.domain

import com.josh25.injectilist.addtasks.data.TaskRepository
import com.josh25.injectilist.ui.model.TaskModel
import javax.inject.Inject


class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    // 152)
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.deleteTask(taskModel)
    }
}