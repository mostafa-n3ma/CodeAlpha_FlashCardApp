package com.example.codealpha_flashcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.codealpha_flashcardapp.presentations.AppNavigator
import com.example.codealpha_flashcardapp.presentations.viewModels.AuthViewModel
import com.example.codealpha_flashcardapp.ui.theme.CodeAlpha_FlashCardAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val authViewModel :AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CodeAlpha_FlashCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator(authViewModel)
                }
            }
        }
    }







}

