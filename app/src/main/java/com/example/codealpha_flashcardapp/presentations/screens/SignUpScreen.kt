package com.example.codealpha_flashcardapp.presentations.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.presentations.AppDestinations
import com.example.codealpha_flashcardapp.presentations.viewModels.AuthViewModel
import com.example.codealpha_flashcardapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel? = null) {



    val announcement = authViewModel!!.announceMessage.collectAsState()
    Log.d("announcment test", "SignInScreen:announsment state value:${announcement.value} ")
    when (announcement.value) {
        "" -> {}
        else -> {
            Log.d("announcment test", "SignInScreen: announcment: ${announcement.value}")
            Toast.makeText(LocalContext.current, announcement.value, Toast.LENGTH_SHORT).show()
            authViewModel.announceMessage("")
        }
    }


    val userIsSignedIn = authViewModel.isUserSignedIn.collectAsState()
    if (userIsSignedIn.value){
        navController.navigate(AppDestinations.HomeScreen.name)
    }





    val name = remember{
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NormalTextComponent(value = stringResource(R.string.hi_there))
        HeadingTextComponent(value = stringResource(R.string.create_account))
        Spacer(modifier = Modifier.height(16.dp))
        EditTextField(
            labelValue = stringResource(R.string.name),
            icon = painterResource(id = R.drawable.user),
            editableTxt = name
        )
        EditTextField(
            labelValue = stringResource(id = R.string.email),
            icon = painterResource(id = R.drawable.email),
            editableTxt = email
        )
        PasswordTextField(
            labelValue = stringResource(id = R.string.paswword),
            icon = painterResource(id = R.drawable.password),
            password = password
        )
        Spacer(modifier = Modifier.height(20.dp))
        ComposableButton(value = stringResource(id = R.string.sign_up), onClick = {
            authViewModel!!.createUser(name.value, email.value,password.value)

//            navController.navigate(AppDestinations.HomeScreen.name)
        })
        AnnotatedTextSignIn(onclick = {
            navController.navigate(AppDestinations.SignInScreen.name)
        })

    }
}

@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen(navController = rememberNavController())
}

@Composable
fun AnnotatedTextSignIn(onclick: () -> Unit) {
    val allreadyHaveAccountTxt = stringResource(id = R.string.allready_have_account)
    val signInTxt = stringResource(id = R.string.sign_in)


    val annotatedString = buildAnnotatedString {
        append(allreadyHaveAccountTxt)
        append(" ")
        withStyle(SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = signInTxt, annotation = signInTxt)
            append(signInTxt)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(top = 8.dp),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if (span.tag == signInTxt) {
                        onclick()
                    }
                }
        })


}