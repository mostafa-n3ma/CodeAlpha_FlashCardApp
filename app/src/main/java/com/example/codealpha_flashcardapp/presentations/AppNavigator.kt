package com.example.codealpha_flashcardapp.presentations

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codealpha_flashcardapp.presentations.screens.DeckDetailsMain
import com.example.codealpha_flashcardapp.presentations.screens.HomeScreenMain
import com.example.codealpha_flashcardapp.presentations.screens.QuizScreen
import com.example.codealpha_flashcardapp.presentations.screens.SignInScreen
import com.example.codealpha_flashcardapp.presentations.screens.SignUpScreen
import com.example.codealpha_flashcardapp.presentations.viewModels.AuthViewModel


@Composable
fun AppNavigator(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    var startDestination = authViewModel.user.collectAsState().let { user ->
        Log.d("test99", "AppNav: user = ${user.value}")
        when (user.value) {
            null -> {
                AppDestinations.SignInScreen.name
            }

            else -> {
                AppDestinations.HomeScreen.name
            }
        }

    }

    Log.d("test99", "AppNav: startDestination = $startDestination")


    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppDestinations.SignInScreen.name) {
            SignInScreen(navController = navController, authViewModel)
        }
        composable(AppDestinations.SignUpScreen.name) {
            SignUpScreen(navController = navController, authViewModel)
        }
        composable(AppDestinations.HomeScreen.name) {
            HomeScreenMain(navController = navController, authViewModel)
        }
        composable(
            route = "${AppDestinations.DeckDetailsScreen.name}/{deckId}",
            arguments = listOf(navArgument("deckId") { type = NavType.IntType })
        ) { backStackEntry ->
            val deckId = backStackEntry.arguments?.getInt("deckId")
            DeckDetailsMain(
                navController = navController,
                deckId = deckId
            )
        }
        composable(
            route = "${AppDestinations.QuizScreen.name}/{deckId}",
            arguments = listOf(navArgument("deckId"){type = NavType.IntType})
        ){backStackEntry->
            val deckId = backStackEntry.arguments?.getInt("deckId")
            QuizScreen(navController = navController, deckId= deckId)

        }

    }


}

enum class AppDestinations {
    SignUpScreen,
    SignInScreen,
    HomeScreen,
    DeckDetailsScreen,
    QuizScreen
}