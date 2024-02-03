package com.example.codealpha_flashcardapp.presentations.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.codealpha_flashcardapp.R
import com.example.codealpha_flashcardapp.presentations.AppDestinations
import com.example.codealpha_flashcardapp.ui.theme.Primary
import com.example.codealpha_flashcardapp.ui.theme.TextColor
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController, authStatus: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalTextComponent(value = stringResource(R.string.welcome_back))
        HeadingTextComponent(value = stringResource(R.string.signin))
        Spacer(modifier = Modifier.height(16.dp))
        EditTextField(
            labelValue = stringResource(R.string.email),
            icon = painterResource(id = R.drawable.email)
        )
        PasswordTextField(
            labelValue = stringResource(id = R.string.paswword),
            icon = painterResource(id = R.drawable.password)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ComposableButton(value = stringResource(id = R.string.sign_in), onClick = {
            // use the navController
            navController.navigate(AppDestinations.HomeScreen.name)
        })
        AnnotatedTextCreateAccount(onclick = {
            navController.navigate(AppDestinations.SignUpScreen.name)
        })

    }
}

@Composable
fun AnnotatedTextCreateAccount(onclick: () -> Unit) {
    val dontHaveAccountTxt = stringResource(id = R.string.dont_have_account)
    val creatAccountTxt = stringResource(id = R.string.create_account)


    val annotatedString = buildAnnotatedString {
        append(dontHaveAccountTxt)
        append(" ")
        withStyle(SpanStyle(color = Primary)){
            pushStringAnnotation(tag = creatAccountTxt, annotation = creatAccountTxt)
            append(creatAccountTxt)
        }
    }

    ClickableText(
        modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center).padding(top = 8.dp),
        text = annotatedString,
        onClick = {offset->
            annotatedString.getStringAnnotations(offset,offset)
                .firstOrNull()?.also { span->
                    if (span.tag == creatAccountTxt){
                        onclick()
                    }
                }
        })


}


@Composable
fun ComposableButton(value: String, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue: String, icon: Painter) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Primary,
            focusedBorderColor = Primary,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = labelValue) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "password leading icon",
                modifier = Modifier.size(32.dp)
            )
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible.value) {
                stringResource(R.string.hide_password)
            } else {
                stringResource(R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )


}

@ExperimentalMaterial3Api
@Composable
fun EditTextField(labelValue: String, icon: Painter) {
    val text = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        keyboardOptions = KeyboardOptions.Default,
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = "email text field",
                modifier = Modifier.size(32.dp)
            )
        }
    )


}


@Preview
@Composable
fun SignInPreview() {
    SignInScreen(navController = rememberNavController(), authStatus = authStatus)
}

@Composable
fun NormalTextComponent(value: String, fontSize: TextUnit = 24.sp) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center

    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
//            .border(BorderStroke(1.dp, GrayColor), shape = RoundedCornerShape(50)),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}