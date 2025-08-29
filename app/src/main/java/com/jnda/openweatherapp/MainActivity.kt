package com.jnda.openweatherapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jnda.openweatherapp.ui.screens.LoginDestination
import com.jnda.openweatherapp.ui.screens.RegisterDestination
import com.jnda.openweatherapp.ui.screens.WeatherListDestination
import com.jnda.openweatherapp.ui.screens.WeatherMainDestination
import com.jnda.openweatherapp.ui.screens.common.CommonBottomNavigation
import com.jnda.openweatherapp.feature.home.ui.WeatherListScreen
import com.jnda.openweatherapp.feature.home.ui.WeatherMainScreen
import com.jnda.openweatherapp.feature.login.ui.LoginScreen
import com.jnda.openweatherapp.feature.login.viewmodel.LoginViewModel
import com.jnda.openweatherapp.ui.screens.mainScreens
import com.jnda.openweatherapp.feature.register.ui.RegisterScreen
import com.jnda.openweatherapp.feature.register.viewmodel.RegisterViewModel
import com.jnda.openweatherapp.location.LocationViewModel
import com.jnda.openweatherapp.ui.screens.common.NormalText
import com.jnda.openweatherapp.ui.theme.OpenWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri
import com.jnda.openweatherapp.feature.home.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    locationViewModel: LocationViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    registerViewModel: RegisterViewModel = viewModel()
) {

    val context = LocalContext.current

    var showContent by remember { mutableStateOf(true) }
    var checkLocationPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.any { p -> p.value }) {
            // revalidate permission
            locationViewModel.checkLocationPermission { p ->
                checkLocationPermission = true
            }
        } else {
            // Show permission dialog
            // init default location
            locationViewModel.getDefaultLocation()
            showPermissionDialog = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        locationViewModel.checkLocationPermission {
//            checkLocationPermission = true
            launcher.launch(locationViewModel.getLocationPermissions())
        }
    }

    if (checkLocationPermission) {
        checkLocationPermission = false
        launcher.launch(locationViewModel.getLocationPermissions())
    }

    if (showPermissionDialog) {
        PermissionDialog(
            onDismiss = { showPermissionDialog = false },
            onConfirm = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${context.packageName}".toUri()
                }
                context.startActivity(intent)
                showPermissionDialog = false
            }
        )
    }

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()

    // fetch current destination
    val currentDestination = currentBackStack?.destination

    val currentScreen = mainScreens.find { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (currentScreen != null) {
                CommonBottomNavigation(
                    mainScreens = mainScreens,
                    onNavSelected = { nav ->
                        navController.navigateSingleTopTo(nav.route)
                    },
                    currentScreen = currentScreen
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WeatherMainDestination.route) {
                WeatherMainScreen(
                    locationViewModel = locationViewModel,
                    weatherViewModel = weatherViewModel
                )
            }

            composable(route = WeatherListDestination.route) {
                WeatherListScreen(weatherViewModel = weatherViewModel)
            }

            composable(route = LoginDestination.route) {
                LoginScreen(navController, loginViewModel = loginViewModel)
            }

            composable(route = RegisterDestination.route) {
                RegisterScreen(navController, registerViewModel = registerViewModel)
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            NormalText(text = "Permission Required")
        },
        text = {
            NormalText(
                "To get the actual weather update to your location, please allow the app's location permission."
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                NormalText("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                NormalText("Cancel")
            }
        }
    )
}