package com.example.todoapp.ui.theme.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoRepository

class TodoDetailViewModelFactory(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoDetailViewModel(repository, todoId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
