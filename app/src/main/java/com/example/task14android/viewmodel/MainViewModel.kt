package com.example.task14android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.task14android.db.TodoDao
import com.example.task14android.db.TodoItemData
import com.example.task14android.db.TodoItemsDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDatabaseDAO: TodoDao

    init {
        todoDatabaseDAO = TodoItemsDatabase.getInstance(application).todoDao()
    }

    fun removeNote(note: TodoItemData) {
        viewModelScope.launch {
            todoDatabaseDAO.delete(note)
        }
    }

    fun getAllNotes(): LiveData<List<TodoItemData>> {
        return todoDatabaseDAO.getAllTodos()
    }

    suspend fun getTodoByID(id: Int): TodoItemData {
        return todoDatabaseDAO.getTodoByID(id)
    }
}