package ru.evka.takeandremember.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoItem(
    val name: String,
    val description: String,
    // http://www.nncron.ru/help/EN/working/cron-format.htm
    val cron: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}