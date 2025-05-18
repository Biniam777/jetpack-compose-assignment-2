package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    val todosFromDb: Flow<List<Todo>> = todoDao.getAllTodos()
    suspend fun getTodos(): List<Todo> {
        return todoDao.getAllTodosOnce()
    }
    suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }



    suspend fun refreshTodos() {
        val todosFromApi = com.example.todoapp.network.RetrofitInstance.api.getTodos()
        todoDao.insertTodos(todosFromApi)

    }
}
