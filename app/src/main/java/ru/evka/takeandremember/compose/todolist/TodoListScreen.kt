package ru.evka.takeandremember.compose.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.evka.takeandremember.R
import ru.evka.takeandremember.data.TodoItem
import ru.evka.takeandremember.data.TodoItemType
import ru.evka.takeandremember.ui.TakeAndRememberTheme
import ru.evka.takeandremember.viewmodels.TodoListViewModel


@Composable
fun TodoListScreen(
    onTodoItemClick: (TodoItem) -> Unit = {},
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todoItems by viewModel.todoItems.observeAsState(initial = emptyList())
    TodoListScreen(
        todoItems = todoItems,
        onTodoItemClick = onTodoItemClick,
        onAddTodoItemClick = { viewModel.addTodoItem() }
    )
}

@Composable
fun TodoListScreen(
    todoItems: List<TodoItem>,
    onTodoItemClick: (TodoItem) -> Unit = {},
    onAddTodoItemClick: () -> Unit = {}
) {
    LazyColumn {
        item {
            Button(onAddTodoItemClick) {
                Text(
                    text = stringResource(R.string.add_item_button)
                )
            }
        }
        items(todoItems) { item ->
            Row(modifier = Modifier.clickable(onClick = { onTodoItemClick(item) } )) {
                Text(
                    text = "${item.name}",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.description,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    TakeAndRememberTheme {
        TodoListScreen(
            todoItems = List(10) { TodoItem("Item #$it", "Description $it", TodoItemType.singleAction, "", 0, 0, 0) }
        )
    }
}