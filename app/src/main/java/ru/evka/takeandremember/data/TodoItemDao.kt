package ru.evka.takeandremember.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todo_items ORDER BY cron")
    fun getTodoItems(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_items WHERE id = :id")
    fun getTodoItem(id: Long): Flow<TodoItem>

    @Upsert
    suspend fun upsertAll(todoItems: List<TodoItem>)

    @Insert
    suspend fun insertTodoItem(todoItem: TodoItem): Long

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)
}