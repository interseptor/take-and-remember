package ru.evka.takeandremember.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoItem(
    val name: String,
    val description: String,
    val type: TodoItemType,
    // http://www.nncron.ru/help/EN/working/cron-format.htm
    val cron: String?, // for TodoItemType.repeatingAction
    val date: Long?, // for TodoItemType.singleAction
    val startTime: Long?, // for TodoItemType.singleAction
    val duration: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    fun deepCopy(
        name: String = this.name,
        description: String = this.description,
        type: TodoItemType = this.type,
        cron: String? = this.cron,
        date: Long? = this.date,
        startTime: Long? = this.startTime,
        duration: Long = this.duration,
        id: Long = this.id
    ): TodoItem {
        val copy = TodoItem(name, description, type, cron, date, startTime, duration)
        copy.id = id
        return copy
    }
}