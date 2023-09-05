package ru.evka.takeandremember.compose.todoitemdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.evka.takeandremember.data.TodoItem
import ru.evka.takeandremember.ui.TakeAndRememberTheme
import ru.evka.takeandremember.viewmodels.TodoItemDetailViewModel

@Composable
fun TodoItemDetailScreen(
    todoItemDetailViewModel: TodoItemDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val todoItem = todoItemDetailViewModel.todoItem.observeAsState().value

    if (todoItem != null) {
        TodoItemDetail(
            todoItem = todoItem,
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
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Column {
        Text(
            todoItem.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            todoItem.description,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onBackClick) {
            Text(
                text = "Back",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
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

@Preview
@Composable
fun TodoItemDetailPreview() {
    TakeAndRememberTheme {
        TodoItemDetail(
            TodoItem("Name", "Description", "")
        )
    }
}