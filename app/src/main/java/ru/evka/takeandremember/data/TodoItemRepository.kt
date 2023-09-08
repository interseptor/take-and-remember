package ru.evka.takeandremember.data

import javax.inject.Inject
import javax.inject.Singleton
/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [TodoItemDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class TodoItemRepository @Inject constructor(
    private val todoItemDao: TodoItemDao
) {
    suspend fun createTodoItem(name: String, description: String, type: TodoItemType, cron: String?, date: Long?, startTime: Long?, duration: Long) {
        val gardenPlanting = TodoItem(name, description, type, cron, date, startTime, duration)
        todoItemDao.insertTodoItem(gardenPlanting)
    }

    fun getTodoItem(id: Long) = todoItemDao.getTodoItem(id)

    suspend fun updateTodoItem(todoItem: TodoItem) {
        todoItemDao.updateTodoItem(todoItem)
    }

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

