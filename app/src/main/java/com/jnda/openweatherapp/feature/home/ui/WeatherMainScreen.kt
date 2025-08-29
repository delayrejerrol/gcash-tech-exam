package com.jnda.openweatherapp.feature.home.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jnda.openweatherapp.R
import com.jnda.openweatherapp.feature.home.viewmodel.WeatherViewModel
import com.jnda.openweatherapp.location.LocationViewModel
import com.jnda.openweatherapp.ui.screens.common.BoldText
import com.jnda.openweatherapp.ui.screens.common.MediumText
import com.jnda.openweatherapp.ui.screens.common.NormalText
import com.jnda.openweatherapp.ui.theme.c_575DFB
import com.jnda.openweatherapp.ui.theme.c_A4A7F1
import com.jnda.openweatherapp.ui.theme.c_FFFFFF

@Composable
fun WeatherMainScreen(
    locationViewModel: LocationViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val userLocationDTO by locationViewModel.userLocationDTO.collectAsState()
    val weatherDTO by weatherViewModel.weatherDTO.collectAsState()
    var isWeatherRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(userLocationDTO) {
        weatherViewModel.getWeather(userLocationDTO.latitude.toString(), userLocationDTO.longitude.toString())
    }

    LaunchedEffect(weatherDTO) {
        if (isWeatherRefreshing) {
            isWeatherRefreshing = false
        }
    }

    val linearGradientBrush = Brush.verticalGradient(
        colors = listOf(
            c_575DFB,
            c_A4A7F1,
            c_FFFFFF
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(brush = linearGradientBrush)
            .fillMaxWidth()
    ) {
        NormalText("My Location", fontSize = 24.sp, color = c_FFFFFF, modifier = Modifier.padding(top = 40.dp))
        Spacer(modifier = Modifier.padding(8.dp))
        NormalText(
            text = "${userLocationDTO.city}, ${userLocationDTO.countryCode}",
            fontSize = 14.sp, color = c_FFFFFF,
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_refresh_24),
            contentDescription = "My App Icon", // Essential for accessibility
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    if (!isWeatherRefreshing) {
                        weatherViewModel.refreshWeather(
                            userLocationDTO.latitude.toString(),
                            userLocationDTO.longitude.toString()
                        )
                        isWeatherRefreshing = true
                    }
                }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        BoldText(weatherDTO?.temp.orEmpty(), fontSize = 70.sp, color = c_FFFFFF)

        val imageUrl = weatherDTO?.getIconUrl()
        Log.d("WeatherIcon", "Image URL: $imageUrl")

        // Basic AsyncImage usage
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image loaded from URL",
            modifier = Modifier
                .size(70.dp)
                .padding(top = 4.dp),
            contentScale = ContentScale.Crop, // Adjust as needed // Replace with your error drawable
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = c_A4A7F1),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(182.dp)
                    .height(65.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sunrise),
                        contentDescription = "My App Icon", // Essential for accessibility
                        modifier = Modifier
                            .size(28.dp) // Set the size of the image
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        NormalText("Sunrise", fontSize = 12.sp)
                        MediumText(weatherDTO?.sunriseTime.orEmpty(), fontSize = 16.sp)
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = c_A4A7F1),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(182.dp)
                    .height(65.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sunset),
                        contentDescription = "My App Icon", // Essential for accessibility
                        modifier = Modifier
                            .size(28.dp) // Set the size of the image
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        NormalText("Sunset", fontSize = 12.sp)
                        MediumText(weatherDTO?.sunsetTime.orEmpty(), fontSize = 16.sp)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeatherMainScreenPreview() {
    WeatherMainScreen()
}