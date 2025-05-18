package com.example.todoapp.ui.theme.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoDetailUiState {
    object Loading : TodoDetailUiState()
    data class Success(val todo: Todo) : TodoDetailUiState()
    data class Error(val message: String) : TodoDetailUiState()
}

class TodoDetailViewModel(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoDetailUiState>(TodoDetailUiState.Loading)
    val uiState: StateFlow<TodoDetailUiState> = _uiState

    init {
        loadTodo()
    }

    private fun loadTodo() {
        viewModelScope.launch {
            _uiState.value = TodoDetailUiState.Loading
            try {
                // Refresh data from network
                repository.refreshTodos()

                // Load specific todo directly (no collect)
                val todo = repository.getTodoById(todoId)
                _uiState.value = if (todo != null) {
                    TodoDetailUiState.Success(todo)
                } else {
                    TodoDetailUiState.Error("Todo not found.")
                }
            } catch (e: Exception) {
                _uiState.value = TodoDetailUiState.Error("Failed to load todo details.")
            }
        }
    }
}
