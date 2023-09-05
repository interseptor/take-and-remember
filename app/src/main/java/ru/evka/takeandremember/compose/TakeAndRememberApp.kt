package ru.evka.takeandremember.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.evka.takeandremember.compose.todoitemdetail.TodoItemDetailScreen
import ru.evka.takeandremember.compose.todolist.TodoListScreen

@Composable
fun TakeAndRememberApp() {
    val navController = rememberNavController()
    TakeAndRememberNavHost(
        navController
    )
}

@Composable
fun TakeAndRememberNavHost(
    navController: NavHostController
) {
    //val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "todolist") {
        composable("todolist") {
            TodoListScreen(
                onTodoItemClick = {
                    navController.navigate("todoItemDetail/${it.id}")
                }
            )
        }
        composable(
            "todoItemDetail/{todoItemId}",
            arguments = listOf(navArgument("todoItemId") {
                type = NavType.LongType
            })
        ) {
            TodoItemDetailScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}