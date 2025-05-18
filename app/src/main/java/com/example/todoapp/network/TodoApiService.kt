package com.example.todoapp.network

import com.example.todoapp.data.Todo
import retrofit2.http.GET

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}
