package com.example.task14android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.task14android.db.TodoDao
import com.example.task14android.db.TodoItemData
import com.example.task14android.db.TodoItemsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogDetViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDatabaseDAO: TodoDao

    init {
        todoDatabaseDAO = TodoItemsDatabase.getInstance(application).todoDao()
    }

    suspend fun addTodo(note: TodoItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDatabaseDAO.insert(note)
        }
    }

    suspend fun updateTodo(note: TodoItemData) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDatabaseDAO.update(note)
        }
    }

}