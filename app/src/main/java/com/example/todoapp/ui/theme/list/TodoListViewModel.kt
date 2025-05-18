package com.example.todoapp.ui.theme.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoListUiState {
    object Loading : TodoListUiState()
    data class Success(val todos: List<Todo>) : TodoListUiState()
    data class Error(val message: String) : TodoListUiState()
}

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState: StateFlow<TodoListUiState> = _uiState

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.value = TodoListUiState.Loading
            try {
                repository.refreshTodos() // Tries to fetch from network
                val todos = repository.getTodos()
                _uiState.value = TodoListUiState.Success(todos)
            } catch (e: Exception) {
                val cached = repository.getTodos()
                if (cached.isNotEmpty()) {
                    _uiState.value = TodoListUiState.Success(cached)
                } else {
                    _uiState.value = TodoListUiState.Error("Failed to load TODOs.")
                }
            }
        }
    }
}
