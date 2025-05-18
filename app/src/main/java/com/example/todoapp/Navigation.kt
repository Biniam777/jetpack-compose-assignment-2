package com.example.todoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.ui.theme.detail.TodoDetailScreen
import com.example.todoapp.ui.theme.detail.TodoDetailViewModel
import com.example.todoapp.ui.theme.detail.TodoDetailViewModelFactory
import com.example.todoapp.ui.theme.list.TodoListScreen
import com.example.todoapp.ui.theme.list.TodoListViewModel
import com.example.todoapp.ui.theme.list.TodoListViewModelFactory

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    repository: TodoRepository // Pass repository from TodoApp.kt
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "todo_list",
        modifier = modifier
    ) {
        composable("todo_list") {
            val listViewModel: TodoListViewModel = viewModel(
                factory = TodoListViewModelFactory(repository)
            )
            TodoListScreen(
                viewModel = listViewModel,
                onTodoClick = { todoId ->
                    navController.navigate("todo_detail/$todoId")
                }
            )
        }

        composable(
            route = "todo_detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            val detailViewModel: TodoDetailViewModel = viewModel(
                factory = TodoDetailViewModelFactory(repository, todoId)
            )
            TodoDetailScreen(
                viewModel = detailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
