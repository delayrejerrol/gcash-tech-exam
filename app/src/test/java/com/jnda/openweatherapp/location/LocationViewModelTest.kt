package com.jnda.openweatherapp.location

import app.cash.turbine.test
import com.jnda.openweatherapp.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val provider: LocationProvider = mockk(relaxed = true)
    private lateinit var viewModel: LocationViewModel

    @Test
    fun `when onLocationResult is called, userLocationDTO should be updated`() = runTest {
        // Arrange
        val capturedCallback = slot<(UserLocationDTO) -> Unit>()
        every { provider.addOnLocationResult(capture(capturedCallback)) } answers { }

        viewModel = LocationViewModel(provider)

        val testLocation = UserLocationDTO(51.5074, -0.1278)

        capturedCallback.captured.invoke(testLocation)

        viewModel.userLocationDTO.test {
            assertEquals(testLocation, awaitItem())
        }
    }
}