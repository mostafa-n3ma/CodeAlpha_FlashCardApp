package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCard
import com.example.codealpha_flashcardapp.ui.theme.Primary
import com.example.codealpha_flashcardapp.ui.theme.Transparent_50


val FlashCard = FlashCard(id = 0 , question = "are you good developer?", answer = "Yes", Deck_id = 1, hasOptions = false, options = emptyList())
var FlashCard2 = FlashCard(id = 1 , question = "Whats your name?", answer = "mostafa", Deck_id = 1, hasOptions = true, options = listOf("jena", "nema", "ali"))


@Composable
fun QuizScreen(navController: NavController) {

    // replace with the score mutable state
    val score = remember {
        mutableIntStateOf(0)
    }

    // replace background color with the deck color
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary)
            .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //replace with Deck title
            Text(
                text = "Score:${score.value}",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            QuizCardItem(
                flashCard = FlashCard,
            )

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCardItem(flashCard: FlashCard) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Transparent_50, shape = RoundedCornerShape(5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = flashCard.question,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier= Modifier.padding(8.dp)
        )

        AnimatedVisibility(visible = !flashCard.hasOptions, enter = fadeIn(tween(1000))) {
            val answerTxt = remember {
                mutableStateOf("")
            }
            OutlinedTextField(
                value = answerTxt.value,
                onValueChange = { answerTxt.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = { Text(text = "answer") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary,
                    containerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default,
            )

        }



        AnimatedVisibility(
            visible = flashCard.hasOptions,
            enter = fadeIn(tween(1000))) {
            if(flashCard.options.isNotEmpty()){
                val s: String =flashCard.options[0]
                val selectedOption = remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    flashCard.options.forEach { option ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (option == selectedOption.value),
                                onClick = { selectedOption.value = option }
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                }

            }


        }

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding(top = 16.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10)),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary, contentColor = Color.Black
            ),
        ) {
            Text(
                text = stringResource(id = R.string.submit), style = TextStyle(
                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }


    }


@Preview
@Composable
fun QuizPreview() {
    QuizScreen(navController = rememberNavController())
}