package com.josh25.injectilist.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josh25.injectilist.addtasks.domain.AddTaskUseCase
import com.josh25.injectilist.addtasks.domain.GetTasksUseCase
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
    getTasksUseCase: GetTasksUseCase
): ViewModel() {

    // 149) StateFlow
    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map ( ::Success )
        .catch{ TaskUiState.Error(it) }
        .stateIn(viewModelScope,  SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)
    // Converts Flow -> StateFlow // Cancels/Blocks flow when the app exceeds +5sec in the background

    private val _showDialog = MutableLiveData<Boolean>()
    val showdialog:LiveData<Boolean> = _showDialog

 //   private val _tasks = mutableStateListOf<TaskModel>()
 //   val tasks: List<TaskModel> = _tasks

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

    fun onCheckBoxSelected(taskModel: TaskModel) {
        /*
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected) // .copy triggers recompo
        }

         */
    }

    fun onItemRemoved(taskModel: TaskModel) {
        /*
        val task = _tasks.find {it.id == taskModel.id}
        _tasks.remove(task)
         */
    }

}