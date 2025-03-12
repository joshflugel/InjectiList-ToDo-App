package com.josh25.injectilist.addtasks.domain

import com.josh25.injectilist.addtasks.data.TaskRepository
import com.josh25.injectilist.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.add(taskModel)
    }
}