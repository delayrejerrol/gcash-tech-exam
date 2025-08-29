package com.jnda.openweatherapp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jnda.openweatherapp.ext.DateExtension
import com.jnda.openweatherapp.ext.toTitleCase
import com.jnda.openweatherapp.feature.home.viewmodel.WeatherViewModel
import com.jnda.openweatherapp.ui.screens.common.BoldText
import com.jnda.openweatherapp.ui.screens.common.MediumText
import com.jnda.openweatherapp.ui.screens.common.NormalText
import com.jnda.openweatherapp.ui.theme.c_A4A7F1

@Composable
fun WeatherListScreen(
    weatherViewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val items by weatherViewModel.weatherItemDTO.collectAsState()
    weatherViewModel.getWeathers()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->

            WeatherItem(
                DateExtension.getFormattedDate(item.dateTime.toLong()),
                item.description.toTitleCase(),
                item.temp,
                item.getIconUrl())
        }
    }
}

@Composable
fun WeatherItem(
    date: String,
    description: String,
    temperature: String,
    imageUrl: String, // or ImageVector / rememberAsyncImagePainter for URL
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
                .background(
                    color = c_A4A7F1,
                    shape = RoundedCornerShape(8.dp) // adjust if bg has shape
                )
        ) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                NormalText(
                    text = date,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                MediumText(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }

            BoldText(
                text = temperature,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 12.dp)
            )
        }

        // Basic AsyncImage usage
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image loaded from URL",
            modifier = Modifier
                .size(54.dp)
                .align(Alignment.TopEnd)
                .padding(end = 24.dp),
            contentScale = ContentScale.Crop, // Adjust as needed // Replace with your error drawable
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherListScreenPreview() {
    WeatherListScreen()
}

@Preview(showBackground = true)
@Composable
fun WeatherItemPreview() {
    WeatherItem("Today 12:00 PM", "Cloudy and Sunny", "36", "")
}