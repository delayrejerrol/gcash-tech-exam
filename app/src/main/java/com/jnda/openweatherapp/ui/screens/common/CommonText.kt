package com.jnda.openweatherapp.ui.screens.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jnda.openweatherapp.R

@Composable
fun NormalText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    isItalic: Boolean = false,
    color: Color = Color.Black,
) {
    var font = R.font.roboto_regular
    if (isItalic) {
        font = R.font.roboto_italic
    }
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(font)),
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        modifier = modifier,
        color = color,
    )
}

@Preview(showBackground = true, name = "Normal Text Preview")
@Composable
fun NormalTextPreview() {
    NormalText("This is a normal text")
}

@Composable
fun MediumText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    isItalic: Boolean = false,
    color: Color = Color.Black,
) {
    var font = R.font.roboto_medium
    if (isItalic) {
        font = R.font.roboto_medium_italic
    }
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(font)),
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        modifier = modifier,
        color = color,
    )
}

@Preview(showBackground = true, name = "Medium Text Preview")
@Composable
fun MediumTextPreview() {
    MediumText("This is a medium text")
}

@Composable
fun BoldText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    isItalic: Boolean = false,
    color: Color = Color.Black,
) {
    var font = R.font.roboto_bold
    if (isItalic) {
        font = R.font.roboto_bold_italic
    }
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(font)),
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        modifier = modifier,
        color = color
    )
}

@Preview(showBackground = true, name = "Bold Text Preview")
@Composable
fun BoldTextPreview() {
    BoldText("This is a bold text")
}