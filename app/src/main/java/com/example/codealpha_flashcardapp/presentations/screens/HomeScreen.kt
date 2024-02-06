package com.example.codealpha_flashcardapp.presentations.screens

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.operations.data_mangment.Deck
import com.example.codealpha_flashcardapp.presentations.AppDestinations
import com.example.codealpha_flashcardapp.presentations.viewModels.AuthViewModel
import com.example.codealpha_flashcardapp.presentations.viewModels.HomeViewModel
import com.example.codealpha_flashcardapp.ui.theme.LightGray
import com.example.codealpha_flashcardapp.ui.theme.Primary
import com.example.codealpha_flashcardapp.ui.theme.TextColor
import com.example.codealpha_flashcardapp.ui.theme.getColorFromGallery
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMain(navController: NavController, authViewModel: AuthViewModel) {

// the sheet state which starts with
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
            NewDeckBottomSheet(onDonClicked = {
                scope.launch {
                    sheetState.collapse()
                }
            })
        },

//        sheetBackgroundColor = LightGray,
        // the height where the sheet will be while the stable collapse state
        sheetPeekHeight = 0.dp
    ) {
//            screen Content
        // the remain part of the screen without the bottomSheet where you can call the screen composable function
        HomeScreenBase(
            navController = navController,
            authViewModel = authViewModel,
            onCreateDeckClicked = {
                scope.launch {
                    sheetState.expand()
                }
            })

    }


}

@Composable
fun NewDeckBottomSheet(onDonClicked: () -> Unit) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 8.dp)
            .background(LightGray, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title = remember {
            mutableStateOf("")
        }

        val description = remember {
            mutableStateOf("")
        }


        HeadLineTxt(value = "New Deck")



        NormalEditTextField(
            labelValue = "Title",
            editableTxt = title,
            costumeModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            singleLine = true,
            maxLines = 1
        )

        NormalEditTextField(
            labelValue = "Description",
            editableTxt = description,
            costumeModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            singleLine = false,
            maxLines = 5
        )


        ComposableButton(value = "Done", onClick = {
            homeViewModel.addNewDeck(title.value, description.value)
            onDonClicked()
        })


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalEditTextField(
    labelValue: String,
    editableTxt: MutableState<String>,
    costumeModifier: Modifier,
    singleLine: Boolean,
    maxLines: Int
) {
    OutlinedTextField(
        value = editableTxt.value,
        onValueChange = { editableTxt.value = it },
        modifier = costumeModifier,
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        singleLine = singleLine, // Allow multiple lines
        maxLines = maxLines,

        )

}


@Composable
fun HomeScreenBase(
    navController: NavController,
    authViewModel: AuthViewModel? = null,
    onCreateDeckClicked: () -> Unit
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    if (authViewModel!!.firebaseAuth.currentUser != null) {
        homeViewModel.getUserName(authViewModel.firebaseAuth.currentUser!!.email!!)
    }

    val activeUserName = homeViewModel.activeUserName.collectAsState()
    OnBackButtonClicked()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadLineTxt(value = stringResource(id = R.string.hello))
                IconButton(onClick = {
                    // replace with logout action
                    authViewModel!!.signOut()
                    navController.navigate(AppDestinations.SignInScreen.name)
                }) {
                    Icon(imageVector = Icons.Filled.Logout, contentDescription = "")
                }

            }

            UserText(activeUserName = activeUserName)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Text(text = "Pick a Card Deck")
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )


            val decksList: State<List<Deck>?> = homeViewModel._decksList.observeAsState()
            //replace with lazyColumn after getting the list from the viewModel

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (decksList.value != null) {
                    items(decksList.value!!) { deck ->
                        DeckItemCard(
                            title = deck.title,
                            cards_count = 0,
                            color = getColorFromGallery(deck.bg_color),
                            onClick = {
                                navController.navigate("${AppDestinations.DeckDetailsScreen.name}/${deck.id}")
                                Log.d(
                                    "nav arg test",
                                    "HomeScreenBase: ${AppDestinations.DeckDetailsScreen.name}/${deck.id}"
                                )
                            },
                            onMoreBtnClicked = {
                                homeViewModel.deleteDeck(deck)
                            }
                        )
                    }
                }
            }


        }


        CreateNewButton(
            "New Deck", onClick = { onCreateDeckClicked() },
            Modifier
                .padding(16.dp)
                .shadow(2.dp, shape = RoundedCornerShape(10))
                .align(Alignment.BottomEnd)
        )
    }

}

@Composable
fun CreateNewButton(value: String, onClick: () -> Unit, modifier: Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, contentColor = Color.Black
        ),
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
        Text(
            text = value,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun DeckItemCard(
    title: String,
    cards_count: Int,
    onClick: () -> Unit,
    color: Color,
    onMoreBtnClicked: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .shadow(5.dp, shape = RoundedCornerShape(5))
        .background(color = color, shape = RoundedCornerShape(5))
        .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
                .heightIn(max = 25.dp)
                .align(Alignment.TopStart),
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
            )
        )
        Text(
            text = "$cards_count Cards",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = TextColor,
            ),
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
                .heightIn(max = 25.dp)
                .align(Alignment.BottomStart),
        )


//
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
                        onMoreBtnClicked()
                        isExpended.value = false
                    }) {
                    Text(text = "Delete")
                }
            }
        }


        ////


        Icon(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)

        )


    }
}


@Composable
fun HeadLineTxt(value: String) {
    Text(
        text = value,
        style = TextStyle(
            color = TextColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
    )
}


@Composable
fun UserText(activeUserName: State<String>) {
    HeadLineTxt(value = activeUserName.value)
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenBase(navController = rememberNavController(), onCreateDeckClicked = {})

}