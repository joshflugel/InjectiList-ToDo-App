package com.josh25.injectilist.ui

import com.josh25.injectilist.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading: TaskUiState
    data class Error(val throwable: Throwable): TaskUiState
    data class Success(val tasks:List<TaskModel>): TaskUiState
}