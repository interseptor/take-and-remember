package ru.evka.takeandremember.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class TodoListViewState(
    val items: List<String> = ArrayList()
)

class TodoListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListViewState())
    val uiState: StateFlow<TodoListViewState> = _uiState.asStateFlow()

    fun createRandomList() {
        val count = Random.nextInt(1, 10)
        val items = ArrayList<String>()
        for (i in 0 until count) {
            items.add("Item #${i}")
        }
        _uiState.update { currentState -> currentState.copy(items = items) }
    }
}