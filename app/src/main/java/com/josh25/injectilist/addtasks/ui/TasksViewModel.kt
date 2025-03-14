package com.josh25.injectilist.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josh25.injectilist.addtasks.domain.AddTaskUseCase
import com.josh25.injectilist.addtasks.domain.DeleteTaskUseCase
import com.josh25.injectilist.addtasks.domain.GetTasksUseCase
import com.josh25.injectilist.addtasks.domain.UpdateTaskUseCase
import com.josh25.injectilist.ui.TaskUiState
import com.josh25.injectilist.ui.TaskUiState.Success
import com.josh25.injectilist.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase, // 150)
    private val deleteTaskUseCase: DeleteTaskUseCase // 152
): ViewModel() {

    // 149) StateFlow
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map ( ::Success )
        .catch{ TaskUiState.Error(it) }
        .stateIn(viewModelScope,  SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)
    // Converts Flow -> StateFlow // Cancels/Blocks flow when the app exceeds +5sec in the background

    private val _showDialog = MutableLiveData<Boolean>()
    val showdialog:LiveData<Boolean> = _showDialog

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(task: String) {
        _showDialog.value = false
        Log.i("joshtag", "TaskCreated")
        // 149) StateFlow
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) { // 150
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemDeleted(taskModel: TaskModel) { // 152
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

}