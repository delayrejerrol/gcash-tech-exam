package com.jnda.openweatherapp.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jnda.openweatherapp.ui.theme.c_575DFB

@Composable
fun OutlinedInputField(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
//        NormalText(text = title)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = c_575DFB,
                unfocusedBorderColor = c_575DFB,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedInputFieldPreview() {
    OutlinedInputField(title = "Username", placeholder = "Enter your username", "", {})
}

@Composable
fun OutlinedPasswordInputField(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
//        NormalText(text = title)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = c_575DFB,
                unfocusedBorderColor = c_575DFB,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedPasswordInputFieldPreview() {
    OutlinedPasswordInputField(title = "Password", placeholder = "*****", "", {})
}