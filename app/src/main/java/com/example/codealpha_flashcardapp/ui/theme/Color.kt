package com.example.codealpha_flashcardapp.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Primary = Color(0xFF92A3FD)
val Secondary = Color(0xFF9DCEFF)
val TextColor = Color(0xFF1D1617)
val AccentColor = Color(0xFFC58BF2)
val Transparent_50 = Color(0x80FFFFFF)
val Transparent_30 = Color(0x4DFFFFFF)
val LightGray = Color(0xB9DDDDDD)




val decksColors = listOf<Color>(
    Color(0xFFE91E63),
    Color(0xFFCDDC39),
    Color(0xFF2196F3),
    Color(0xFFF44336),
    Color(0xFF673AB7),
    Color(0xB9FF00DD),
    Color(0xB900FFFF),
)



fun getColorFromGallery(index:Int):Color{
    return decksColors[index]
}