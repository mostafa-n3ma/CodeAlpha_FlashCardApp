package com.example.codealpha_flashcardapp.presentations.screens

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
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

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            HeadLineTxt(value = stringResource(id = R.string.hello))
            IconButton(onClick = {
                // replace with logout action
            }) {
                Icon(imageVector = Icons.Filled.Logout, contentDescription = "")
            }

        }

        UserText(user_name = "Mostafa")
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


        //replace with lazyColumn after getting the list from the viewModel
        DeckItemCard(
            title = "Deck Title Title Title TitleTitle Title Title",
            cards_count = 12,
            color = Primary,
            onClick = {
                      navController.navigate(AppDestinations.DeckDetailsScreen.name)
            },
            onMenuClicked = {})


    }
}

@Composable
fun DeckItemCard(
    title: String,
    cards_count: Int,
    onClick: () -> Unit,
    onMenuClicked: () -> Unit,
    color: Color
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


        IconButton(
            onClick = { onMenuClicked },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
        }

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
fun UserText(user_name: String) {
    HeadLineTxt(value = user_name)
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}