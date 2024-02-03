package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.presentations.AppDestinations
import com.example.codealpha_flashcardapp.ui.theme.Primary
import com.example.codealpha_flashcardapp.ui.theme.TextColor
import com.example.codealpha_flashcardapp.ui.theme.Transparent_50
import com.example.codealpha_flashcardapp.ui.theme.Transparent_30

@Composable
fun DeckDetailsScreen(navController: NavController) {


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
            HeadLineTxt(value = "Deck Title")
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

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        // replace with Deck description
        Text(
            text = stringResource(R.string.Lorem_ipsum), style = TextStyle(
                fontSize = 18.sp, color = TextColor, fontWeight = FontWeight.Normal
            )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )


        QuizButton(value = stringResource(id = R.string.start_quiz),
            onClick = { navController.navigate(AppDestinations.QuizScreen.name) })

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )



        //replace with Lazy Column
        FlashCardItem(question = stringResource(id = R.string.Lorem_ipsum), answer = stringResource(
            id = R.string.Lorem_ipsum
        ), onMenuClicked = {})


    }
}

@Composable
fun FlashCardItem(
    question: String, answer: String, onMenuClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Transparent_50, shape = RoundedCornerShape(5)),
    ) {
        val showAnswer = remember {
            mutableStateOf(false)
        }
        val answerBtnTxt = remember {
            mutableStateOf("Answer")
        }

        val arrowIcon = remember {
            mutableStateOf(Icons.Filled.KeyboardArrowDown)
        }


        when (showAnswer.value) {
            true -> {
                answerBtnTxt.value = ""
                arrowIcon.value = Icons.Filled.KeyboardArrowUp

            }

            false -> {
                answerBtnTxt.value = "Answer"
                arrowIcon.value = Icons.Filled.KeyboardArrowDown
            }
        }


        IconButton(
            onClick = { onMenuClicked() }, modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Q: $question", style = TextStyle(
                    fontSize = 16.sp, color = TextColor, fontWeight = FontWeight.Normal
                ), modifier = Modifier
                    .heightIn(min = 100.dp)
                    .widthIn(max = 325.dp)
                    .padding(16.dp)
            )

            AnimatedVisibility(
                visible = showAnswer.value, enter = fadeIn(tween(1000)), exit = fadeOut()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                Text(
                    text = "$answer",
                    modifier = Modifier
                        .heightIn(min = 100.dp)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(color = Transparent_30, shape = RoundedCornerShape(5))
                        .padding(8.dp)
                )


            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Transparent_30,
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
                    .clickable { showAnswer.value = !showAnswer.value },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = answerBtnTxt.value)
                Icon(imageVector = arrowIcon.value, contentDescription = "")
            }
        }


    }
}

@Composable
fun QuizButton(value: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .shadow(2.dp, shape = RoundedCornerShape(10)),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, contentColor = Color.Black
        ),
    ) {
        Text(
            text = value, style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
        )
    }
}


@Preview
@Composable
fun DeckDetailsPreview() {
    DeckDetailsScreen(navController = rememberNavController())
}