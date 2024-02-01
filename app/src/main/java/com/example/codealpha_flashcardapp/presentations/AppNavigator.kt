package com.example.codealpha_flashcardapp.presentations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.presentations.screens.DeckDetailsScreen
import com.example.codealpha_flashcardapp.presentations.screens.HomeScreen
import com.example.codealpha_flashcardapp.presentations.screens.QuizScreen
import com.example.codealpha_flashcardapp.presentations.screens.SignInScreen
import com.example.codealpha_flashcardapp.presentations.screens.SignUpScreen


@Composable
fun AppNavigator(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestinations.SignInScreen.name){
        composable(AppDestinations.SignInScreen.name,){
            SignInScreen(navController= navController)
        }
        composable(AppDestinations.SignUpScreen.name){
            SignUpScreen(navController = navController)
        }
        composable(AppDestinations.HomeScreen.name){
            HomeScreen(navController = navController)
        }
        composable(AppDestinations.DeckDetailsScreen.name){
            DeckDetailsScreen(navController = navController)
        }
        composable(AppDestinations.QuizScreen.name){
            QuizScreen(navController = navController)
        }
    }





}

enum class AppDestinations{
    SignUpScreen,
    SignInScreen,
    HomeScreen,
    DeckDetailsScreen,
    QuizScreen
}