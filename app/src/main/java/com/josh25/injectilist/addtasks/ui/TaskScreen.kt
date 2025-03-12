package com.josh25.injectilist.addtasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.josh25.injectilist.ui.model.TaskModel

@Composable
fun TaskScreen(tasksViewModel: TasksViewModel) {

    val showDialog:Boolean by tasksViewModel.showdialog.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        AddTaskDialog(showDialog, onDismiss = { tasksViewModel.onDialogClose() }, onTaskAdded = {tasksViewModel.onTaskCreated(it)})
        FabDialog(Modifier.align(Alignment.BottomEnd),tasksViewModel)
        TaskList(tasksViewModel)
    }
}

@Composable
fun TaskList(tasksViewModel: TasksViewModel) {
    val myTasks: List<TaskModel> = tasksViewModel.tasks

    LazyColumn {
        items(myTasks, key = {it.id}) { task -> // key optimizes performance
            ItemTask(task, tasksViewModel)
        }
    }
}


@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TasksViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { taskViewModel.onItemRemoved(taskModel) })
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(taskModel.task, modifier = Modifier.weight(1f))
            Checkbox(checked = taskModel.selected, onCheckedChange = {
                taskViewModel.onCheckBoxSelected(taskModel)
            })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel){
    FloatingActionButton(onClick = {
        tasksViewModel.onShowDialogClick()
    }, modifier = modifier.padding(16.dp)) {
        Icon(Icons.Filled.Add, contentDescription = "Add Task")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss:() -> Unit, onTaskAdded:(String) -> Unit) {
    var myTask by rememberSaveable { mutableStateOf("") }
    if(show) {
        Dialog(onDismissRequest = onDismiss) {
            Column(Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)) {
                Text(
                    "Add Task",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = myTask,
                    onValueChange = { myTask = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onTaskAdded(myTask)
                        myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("AddTask")
                }
            }
        }
    }
}