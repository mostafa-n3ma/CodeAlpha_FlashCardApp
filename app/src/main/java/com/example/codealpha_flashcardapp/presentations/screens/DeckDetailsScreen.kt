package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.codealpha_flashcardapp.presentations.AppDestinations

@Composable
fun DeckDetailsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Deck Details Screen")
        Button(onClick = {
            navController.navigate(AppDestinations.QuizScreen.name)
        }) {
            Text(text = "go to Quiz Screen")
        }
    }
}