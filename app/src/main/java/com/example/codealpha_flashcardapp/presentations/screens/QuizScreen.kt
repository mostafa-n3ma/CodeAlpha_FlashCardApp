package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCard
import com.example.codealpha_flashcardapp.presentations.viewModels.QuizViewModel
import com.example.codealpha_flashcardapp.ui.theme.Primary
import com.example.codealpha_flashcardapp.ui.theme.Transparent_50
import com.example.codealpha_flashcardapp.ui.theme.getColorFromGallery




@Composable
fun QuizScreen(navController: NavController, deckId: Int?=null) {

    val quizViewModel:QuizViewModel= hiltViewModel()
    quizViewModel.passDeckId(deckId)
    val deck = quizViewModel.deck.collectAsState()

    val quizCards = quizViewModel.quizCards.collectAsState()
    quizViewModel.onQuizCardsReady()

    val cardAnimation = quizViewModel.cardAnimationState.collectAsState()
    val onScreenCard = quizViewModel.onScreenCard.collectAsState()
    val score = quizViewModel.score.collectAsState()
    val finalScoreVisibility = quizViewModel.finalScoreVisibility.collectAsState()



    // replace with the score mutable state

    // replace background color with the deck color
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getColorFromGallery(deck.value.bg_color))
            .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //replace with Deck title
            AnimatedVisibility(visible = !finalScoreVisibility.value) {
                Text(
                    text = "Score:${score.value}",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

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

            AnimatedVisibility(
                visible = cardAnimation.value,
                enter = slideInHorizontally(initialOffsetX = {it}),
                exit = slideOutHorizontally (targetOffsetX = {-it})
            ) {
                QuizCardItem(
                    flashCard = onScreenCard.value,
                    quizViewModel = quizViewModel
                )
            }


            AnimatedVisibility(visible = finalScoreVisibility.value) {
                HeadLineTxt(value = "${score.value} correct of ${quizCards.value.size}")
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCardItem(flashCard: FlashCard, quizViewModel: QuizViewModel) {

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

        val answerTxt = remember { mutableStateOf("") }
        AnimatedVisibility(visible = !flashCard.hasOptions, enter = fadeIn(tween(1000))) {
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


        val selectedOption = remember { mutableStateOf("") }
        AnimatedVisibility(
            visible = flashCard.hasOptions,
            enter = fadeIn(tween(1000))) {
            if(flashCard.options.isNotEmpty()){
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
            onClick = {
                      when(flashCard.hasOptions){
                          true -> {quizViewModel.passCard(selectedOption.value,flashCard)}
                          false -> {quizViewModel.passCard(answerTxt.value,flashCard)}
                      }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10)),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary, contentColor = Color.Black
            ),
        ) {
            Text(
                text = stringResource(id = R.string.pass), style = TextStyle(
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