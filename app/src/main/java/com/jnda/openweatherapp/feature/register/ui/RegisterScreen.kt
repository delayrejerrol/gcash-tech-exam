package com.jnda.openweatherapp.feature.register.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jnda.openweatherapp.navigateSingleTopTo
import com.jnda.openweatherapp.ui.screens.LoginDestination
import com.jnda.openweatherapp.ui.screens.common.BoldText
import com.jnda.openweatherapp.ui.screens.common.MediumText
import com.jnda.openweatherapp.ui.screens.common.NormalText
import com.jnda.openweatherapp.ui.screens.common.OutlinedInputField
import com.jnda.openweatherapp.ui.screens.common.OutlinedPasswordInputField
import com.jnda.openweatherapp.feature.register.viewmodel.RegisterViewModel
import com.jnda.openweatherapp.ui.theme.c_575DFB
import com.jnda.openweatherapp.ui.theme.c_FFFFFF
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    navController: NavHostController? = null,
    registerViewModel: RegisterViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(key1 = registerViewModel.navigationEvent) {
        registerViewModel.navigationEvent.collectLatest { destination ->
            navController?.navigateSingleTopTo(destination.route)
        }
    }
    Column(
        modifier = Modifier.padding(horizontal = 35.dp)
    ) {
        BoldText(
            text = "Register",
            color = c_575DFB,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 60.dp)
        )

        NormalText(
            text = "Create an account",
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 19.dp)
        )


        Spacer(modifier = Modifier.padding(top = 24.dp))
        OutlinedInputField("Username", placeholder = "Enter your username", username, { username = it })

        Spacer(modifier = Modifier.padding(top = 16.dp))
        OutlinedPasswordInputField("Password", placeholder = "Enter your password", password, { password = it })

        Spacer(modifier = Modifier.padding(top = 70.dp))
        Button(
            onClick = {
                registerViewModel.addUser(username, password)
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = c_575DFB,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            MediumText("Register", color = c_FFFFFF, fontSize = 16.sp)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)) {
            NormalText("Already have an account?")
            Spacer(modifier = Modifier.padding(2.dp))
            NormalText(
                text = "Login",
                color = c_575DFB,
                modifier = Modifier.clickable(
                    onClick = {
                        navController?.navigateSingleTopTo(LoginDestination.route)
                    }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}