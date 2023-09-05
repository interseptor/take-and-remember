package ru.evka.takeandremember.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemRepository @Inject constructor(
    private val todoItemDao: TodoItemDao
) {
    suspend fun createTodoItem(name: String, description: String, cron: String) {
        val gardenPlanting = TodoItem(name, description, cron)
        todoItemDao.insertTodoItem(gardenPlanting)
    }

    fun getTodoItem(id: Long) = todoItemDao.getTodoItem(id)

    suspend fun removeTodoItem(todoItem: TodoItem) {
        todoItemDao.deleteTodoItem(todoItem)
    }

    fun getTodoItems() = todoItemDao.getTodoItems()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TodoItemRepository? = null

        fun getInstance(todoItemDao: TodoItemDao) =
            instance ?: synchronized(this) {
                instance ?: TodoItemRepository(todoItemDao).also { instance = it }
            }
    }
}