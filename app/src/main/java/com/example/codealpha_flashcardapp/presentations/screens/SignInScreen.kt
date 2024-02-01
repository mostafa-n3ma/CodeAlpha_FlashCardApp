package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codealpha_flashcardapp.presentations.AppDestinations

@Composable
fun SignInScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign In Screen")
        Button(onClick = {
            navController.navigate(AppDestinations.SignUpScreen.name)
        },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

        ) {
            Text(text = "go to sign up Screen")
        }
    }

}