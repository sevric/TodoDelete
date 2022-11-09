package com.example.task14android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoItemData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val color: String
)