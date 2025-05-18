package com.example.todoapp.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todos WHERE id = :todoId LIMIT 1")
    suspend fun getTodoById(todoId: Int): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<Todo>)

    @Query("SELECT * FROM todos")
    suspend fun getAllTodosOnce(): List<Todo>

    @Query("DELETE FROM todos")
    suspend fun clearAllTodos()
}

