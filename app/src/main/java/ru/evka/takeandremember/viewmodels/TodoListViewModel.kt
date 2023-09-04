package ru.evka.takeandremember.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.evka.takeandremember.data.TodoItem
import ru.evka.takeandremember.data.TodoItemRepository
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject internal constructor(
    private val todoItemRepository: TodoItemRepository
) : ViewModel() {
    val todoItems: LiveData<List<TodoItem>> =
        todoItemRepository.getTodoItems().asLiveData()

    fun addTodoItem() {
        viewModelScope.launch {
            val index = todoItems.value?.size
            todoItemRepository.createTodoItem("Item #$index", "Description", "")
        }
    }
}