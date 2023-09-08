package ru.evka.takeandremember.compose.todoitemdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.evka.takeandremember.R
import ru.evka.takeandremember.compose.OkCancelDialog
import ru.evka.takeandremember.data.TodoItem
import ru.evka.takeandremember.data.TodoItemType
import ru.evka.takeandremember.ui.TakeAndRememberTheme
import ru.evka.takeandremember.viewmodels.TodoItemDetailViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

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
        TypeSelectionField(todoItem.type) { type -> onTodoItemChange(todoItem.deepCopy(type = type)) }
        if (todoItem.type == TodoItemType.singleAction) {
            StartDateField(todoItem.date ?: 0) { newDate -> onTodoItemChange(todoItem.deepCopy(date = newDate)) }
        }
        StartTimeField(todoItem.startTime ?: 0) { newStartTime -> onTodoItemChange(todoItem.deepCopy(startTime = newStartTime)) }
        DurationField(todoItem.duration) { newDuration -> onTodoItemChange(todoItem.deepCopy(duration = newDuration)) }
        Button(onBackClick, shape = RoundedCornerShape(5.dp)) {
            Text(
                text = stringResource(R.string.back_button)
            )
        }
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
            Text(
                text = stringResource(R.string.delete_button)
            )
        }
    }
}

@Composable
fun TypeSelectionField(todoItemType: TodoItemType, onTodoItemTypeChange: (changedItemType: TodoItemType) -> Unit) {
    Column(Modifier.selectableGroup()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = TodoItemType.singleAction == todoItemType,
                onClick = {
                    if (TodoItemType.singleAction != todoItemType) {
                        onTodoItemTypeChange(TodoItemType.singleAction)
                    }
                }
            )
            Text(
                text = stringResource(R.string.single_action_type),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = TodoItemType.repeatingAction == todoItemType,
                onClick = {
                    if (TodoItemType.repeatingAction != todoItemType) {
                        onTodoItemTypeChange(TodoItemType.repeatingAction)
                    }
                }
            )
            Text(
                text = stringResource(R.string.repeating_action_type),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartDateField(date: Long, onTodoItemDateChange: (newDate: Long) -> Unit) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val formatter = SimpleDateFormat("dd MM yyyy")
    Text(
        text = "${stringResource(R.string.start_date_field)}: ${formatter.format(Date(date))}",
        modifier = Modifier.clickable {
            showDatePicker = true
        },
        color = MaterialTheme.colorScheme.secondary
    )
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onTodoItemDateChange(datePickerState.selectedDateMillis!!)
                }) {
                    Text(text = stringResource(R.string.ok_button))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = stringResource(R.string.cancel_button))
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
fun StartTimeField(startTime: Long, onTodoItemStartTimeChange: (newStartTime: Long) -> Unit) {
    val initialHour = Math.floor(startTime.div(60).toDouble()).toInt()
    val initialMinute = startTime.rem(60).toInt()
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
        text = "${stringResource(R.string.start_time_field)}: ${formatter.format(Date(startTime * 60000))}",
        modifier = Modifier.clickable {
            showTimePicker = true
        },
        color = MaterialTheme.colorScheme.secondary
    )
    if (showTimePicker) {
        OkCancelDialog(
            title = stringResource(R.string.select_time_dialog_title),
            onCancel = {
                showTimePicker = false
            },
            onConfirm = {
                showTimePicker = false
                onTodoItemStartTimeChange((timePickerState.hour * 60 + timePickerState.minute).toLong())
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun DurationField(duration: Long, onTodoItemDurationChange: (newDuration: Long) -> Unit) {
    var inputDuration by remember {
        mutableStateOf(duration)
    }
    var showDurationEditDialog by remember {
        mutableStateOf(false)
    }

    val formatter = SimpleDateFormat("HH:mm")
    Text(
        text = "${stringResource(R.string.duration_field)}: ${formatter.format(Date(duration * 60000))}",
        modifier = Modifier.clickable {
            showDurationEditDialog = true
        },
        color = MaterialTheme.colorScheme.secondary
    )
    if (showDurationEditDialog) {
        val onConfirm = {
            showDurationEditDialog = false
            onTodoItemDurationChange(inputDuration)
        }
        OkCancelDialog(
            title = stringResource(R.string.enter_duration_in_minutes),
            onCancel = {
                showDurationEditDialog = false
            },
            onConfirm = onConfirm
        ) {
            TextField(
                value = inputDuration.toString(),
                onValueChange = {
                    if (it.isEmpty()) {
                        inputDuration = 0
                    } else {
                        var i = it.toLongOrNull()
                        if (i != null) {
                            if (i < 0) {
                                i = 0
                            }
                            if (i > 24 * 60) {
                                i = 24 * 60
                            }
                            i = floor(i.toDouble()).toLong()
                            inputDuration = i
                        }
                    }
                },
                singleLine = true, 
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { onConfirm() })
            )
        }
    }
}

@Preview(showBackground = true, locale = "en")
@Composable
fun TodoItemDetailPreviewEn() {
    TakeAndRememberTheme {
        TodoItemDetail(
            TodoItem("Name", "Description", TodoItemType.singleAction, "", 0, 0, 0)
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun TodoItemDetailPreviewRu() {
    TakeAndRememberTheme {
        TodoItemDetail(
            TodoItem("Name", "Description", TodoItemType.singleAction, "", 0, 0, 0)
        )
    }
}