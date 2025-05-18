package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.room.Room
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room Database
        val database = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()

        // Create Repository with DAO
        val repository = TodoRepository(database.todoDao())

        setContent {
            TodoAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // ✅ Pass repository to AppNavigation
                    AppNavigation(repository = repository)
                }
            }
        }
    }
}
