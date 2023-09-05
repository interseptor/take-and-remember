package ru.evka.takeandremember.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.evka.takeandremember.data.TodoItemRepository
import javax.inject.Inject

@HiltViewModel
class TodoItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoItemRepository: TodoItemRepository
) : ViewModel() {

    val todoItemId: Long = savedStateHandle.get<Long>(TODO_ITEM_ID_SAVED_STATE_KEY)!!

    val todoItem = todoItemRepository.getTodoItem(todoItemId).asLiveData()

    fun deleteTodoItem() {
        viewModelScope.launch {
            todoItem.value?.let { todoItemRepository.removeTodoItem(it) }
        }
    }

    companion object {
        private const val TODO_ITEM_ID_SAVED_STATE_KEY = "todoItemId"
    }
}