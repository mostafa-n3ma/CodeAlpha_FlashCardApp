package com.example.codealpha_flashcardapp.presentations.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.presentations.AppDestinations
import com.example.codealpha_flashcardapp.presentations.viewModels.DeckDetailsViewModel
import com.example.codealpha_flashcardapp.ui.theme.TextColor
import com.example.codealpha_flashcardapp.ui.theme.Transparent_50
import com.example.codealpha_flashcardapp.ui.theme.Transparent_30
import com.example.codealpha_flashcardapp.ui.theme.getColorFromGallery
import kotlinx.coroutines.launch
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.codealpha_flashcardapp.operations.data_mangment.Deck
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCard
import com.example.codealpha_flashcardapp.ui.theme.LightGray

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DeckDetailsMain(navController: NavController, deckId: Int? = null) {

    val deckDetailsViewModel: DeckDetailsViewModel = hiltViewModel()

    deckDetailsViewModel.passDeckId(deckId)
    Log.d("nav arg test", "DeckDetailsScreen: deckId=$deckId")

    val deck = deckDetailsViewModel.deck.collectAsState()


    val sheetState: BottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    // scaffold state which control the whole scaffold and get the sheetState argument
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    //scope will be needed to execute collapse and expend actions because they are suspended functions
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        //first pass the scaffoldState
        scaffoldState = scaffoldState,
        sheetContent = {
            //bottomSheet Content
            // where every thing you want to but in the bottom sheet
            NewCardBottomSheet(deckDetailsViewModel = deckDetailsViewModel,
                onDonClicked = {
                    scope.launch {
                        sheetState.collapse()
                    }
                })
        },

//        sheetBackgroundColor = LightGray,
        // the height where the sheet will be while the stable collapse state
        sheetPeekHeight = 0.dp
    ) {
        // screen Content


        DeckDetailsScreenBase(
            navController = navController,
            deckDetailsViewModel = deckDetailsViewModel,
            deck = deck.value,
            onCreateCardClicked = {
                scope.launch {
                    sheetState.expand()
                }
            }
        )

    }

}

@Composable
fun NewCardBottomSheet(onDonClicked: () -> Unit, deckDetailsViewModel: DeckDetailsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(horizontal = 8.dp)
            .background(LightGray, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val question = remember {
            mutableStateOf("")
        }

        val answer = remember {
            mutableStateOf("")
        }


        HeadLineTxt(value = "New Card")
        NormalEditTextField(
            labelValue = "question",
            editableTxt = question,
            costumeModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            singleLine = true,
            maxLines = 1
        )

        NormalEditTextField(
            labelValue = "answer",
            editableTxt = answer,
            costumeModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            singleLine = true,
            maxLines = 1
        )

        val hasOptions_state = remember {
            mutableStateOf(false)
        }

        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
            ){
            Checkbox(
                checked = hasOptions_state.value ,
                onCheckedChange = {hasOptions_state.value = !hasOptions_state.value}
            )
            Text(text = "have options?")
        }



        val option1= remember {
            mutableStateOf("")
        }
        val option2= remember {
            mutableStateOf("")
        }
        val option3= remember {
            mutableStateOf("")
        }


        AnimatedVisibility(visible = hasOptions_state.value) {
            Column(
                Modifier.fillMaxWidth()
            ) {
                NormalEditTextField(
                    labelValue = "option1",
                    editableTxt = option1,
                    costumeModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    singleLine = true,
                    maxLines = 1
                )

                NormalEditTextField(
                    labelValue = "option2",
                    editableTxt = option2,
                    costumeModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    singleLine = true,
                    maxLines = 1
                )
                NormalEditTextField(
                    labelValue = "option3",
                    editableTxt = option3,
                    costumeModifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    singleLine = true,
                    maxLines = 1
                )

            }
        }


        ComposableButton(
            value = "Done",
            onClick = {
                deckDetailsViewModel.createNewCard(question.value,answer.value,hasOptions_state.value,option1.value,option2.value,option3.value)
                onDonClicked()
                question.value = ""
                answer.value = ""
                hasOptions_state.value = false
                option1.value = ""
                option2.value = ""
                option3.value = ""
            })


    }

}


@Composable
fun DeckDetailsScreenBase(
    navController: NavController,
    deck: Deck,
    onCreateCardClicked: () -> Unit,
    deckDetailsViewModel: DeckDetailsViewModel? = null
) {


    val cards: State<List<FlashCard>?> = deckDetailsViewModel!!._flashCards.observeAsState()
    Log.d("cards", "DeckDetailsMain:cards: ${cards.value} ")


    // replace background color with the deck color
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(getColorFromGallery(deck.bg_color))
                .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //replace with Deck title
                HeadLineTxt(value = deck.title)
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
                text = deck.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                )
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )


            val context= LocalContext.current
            QuizButton(value = stringResource(id = R.string.start_quiz),
                onClick = {
                    if (cards.value!!.size > 1){
                        navController.navigate("${AppDestinations.QuizScreen.name}/${deck.id}")
                    }else{
                        Toast.makeText(context,"create Cards First",Toast.LENGTH_SHORT).show()
                    }
                })

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(cards.value?: emptyList<FlashCard>()){card->
                    FlashCardItem(question = card.question,
                        answer = card.answer,
                        onMenuClicked = {
                            deckDetailsViewModel.deleteCard(card)
                        })
                }
            }




        }


        CreateNewButton(
            value = "New Card", onClick = { onCreateCardClicked() }, modifier = Modifier
                .padding(16.dp)
                .shadow(
                    2.dp,
                    RoundedCornerShape(10)
                )
                .align(Alignment.BottomEnd)
        )
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

        val isExpended = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)
                .clickable { isExpended.value = true },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")

            DropdownMenu(
                expanded = isExpended.value,
                onDismissRequest = { isExpended.value = false }) {
                DropdownMenuItem(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp),
                    onClick = {
                        onMenuClicked()
                        isExpended.value = false
                    }) {
                    Text(text = "Delete")
                }
            }
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

}