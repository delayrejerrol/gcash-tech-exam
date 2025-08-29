package com.jnda.openweatherapp.ui.screens.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jnda.openweatherapp.ui.screens.CommonDestination

@Composable
fun CommonBottomNavigation(
    mainScreens: List<CommonDestination>,
    onNavSelected: (CommonDestination) -> Unit,
    currentScreen: CommonDestination,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        mainScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    if (screen.id != null) {
                        Icon(
                            painter = painterResource(screen.id!!),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    MediumText(text = screen.name, fontSize = 8.sp)
                },
                selected = currentScreen == screen,
                onClick = { onNavSelected.invoke(screen) }
            )
        }
    }
}