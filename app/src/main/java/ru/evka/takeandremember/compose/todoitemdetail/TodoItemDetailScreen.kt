package ru.evka.takeandremember.compose.todoitemdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.evka.takeandremember.compose.TimePickerDialog
import ru.evka.takeandremember.data.TodoItem
import ru.evka.takeandremember.data.TodoItemType
import ru.evka.takeandremember.ui.TakeAndRememberTheme
import ru.evka.takeandremember.viewmodels.TodoItemDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TodoItemDetailScreen(
    todoItemDetailViewModel: TodoItemDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val todoItem = todoItemDetailViewModel.todoItem.observeAsState().value

    if (todoItem != null) {
        TodoItemDetail(
            todoItem = todoItem,
            onTodoItemChange = {
                todoItemDetailViewModel.updateTodoItem(it)
            },
            onBackClick = onBackClick,
            onDeleteClick = {
                todoItemDetailViewModel.deleteTodoItem()
                onBackClick()
            }
        )
    }
}

@Composable
fun TodoItemDetail(
    todoItem: TodoItem,
    onTodoItemChange: (changedItem: TodoItem) -> Unit = {},
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            todoItem.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            todoItem.description,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        TypeSelectionField(todoItem, onTodoItemChange)
        if (todoItem.type == TodoItemType.singleAction) {
            StartDateField(todoItem, onTodoItemChange)
        }
        StartTimeField(todoItem, onTodoItemChange)
        Button(onBackClick, shape = RoundedCornerShape(5.dp)) {
            Text(
                text = "Back",
                color = Color.Black
            )
        }
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
            Text(
                text = "Delete",
                color = Color.Black
            )
        }
    }
}

@Composable
fun TypeSelectionField(todoItem: TodoItem, onTodoItemChange: (changedItem: TodoItem) -> Unit) {
    Column(Modifier.selectableGroup()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = TodoItemType.singleAction == todoItem.type,
                onClick = {
                    if (TodoItemType.singleAction != todoItem.type) {
                        onTodoItemChange(todoItem.deepCopy(type = TodoItemType.singleAction))
                    }
                }
            )
            Text(
                text = "Single Action",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = TodoItemType.repeatingAction == todoItem.type,
                onClick = {
                    if (TodoItemType.repeatingAction != todoItem.type) {
                        onTodoItemChange(todoItem.deepCopy(type = TodoItemType.repeatingAction))
                    }
                }
            )
            Text(
                text = "Repeating Action",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartDateField(todoItem: TodoItem, onTodoItemChange: (changedItem: TodoItem) -> Unit) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = todoItem.date ?: 0)
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val formatter = SimpleDateFormat("dd MM yyyy")
    Text(
        text = "Start date: ${formatter.format(Date(todoItem.date ?: 0))}",
        modifier = Modifier.clickable {
            showDatePicker = true
        }
    )
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onTodoItemChange(todoItem.deepCopy(date = datePickerState.selectedDateMillis!!))
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTimeField(todoItem: TodoItem, onTodoItemChange: (changedItem: TodoItem) -> Unit) {
    val initialHour = Math.floor((todoItem.startTime?.div(60) ?: 0).toDouble()).toInt()
    val initialMinute = (todoItem.startTime?.rem(60) ?: 0).toInt()
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )
    var showTimePicker by remember {
        mutableStateOf(false)
    }
    val formatter = SimpleDateFormat("HH:mm")
    Text(
        text = "Start time: ${formatter.format(Date((todoItem.startTime ?: 0) * 60000))}",
        modifier = Modifier.clickable {
            showTimePicker = true
        }
    )
    if (showTimePicker) {
        TimePickerDialog(
            onCancel = {
                showTimePicker = false
            },
            onConfirm = {
                showTimePicker = false
                onTodoItemChange(todoItem.deepCopy(startTime = (timePickerState.hour * 60 + timePickerState.minute).toLong()))
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Preview
@Composable
fun TodoItemDetailPreview() {
    TakeAndRememberTheme {
        TodoItemDetail(
            TodoItem("Name", "Description", TodoItemType.singleAction, "", 0, 0, 0)
        )
    }
}