package com.example.task14android.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoItem: TodoItemData)

    @Update
    suspend fun update(todoItem: TodoItemData)

//    @Query("DELETE FROM todo_table WHERE id = :id")
//    fun delete(id: Int)
    @Delete
    fun delete(todoItem: TodoItemData)

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<TodoItemData>>

    @Query("SELECT * FROM todo_table WHERE id=:id")
    suspend fun getTodoByID(id: Int): TodoItemData
}