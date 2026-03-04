package com.example.gosleep.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gosleep.ui.components.Context_Logic
import com.example.gosleep.ui.components.Dashboard
import com.example.gosleep.ui.components.Privacy_By_Design
import com.example.gosleep.ui.components.Settings
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.ui.components.navigationBarLayout
import com.example.gosleep.ui.components.TopScreen
import java.time.LocalDateTime
import com.example.gosleep.ui.theme.White
import com.example.gosleep.ui.theme.Charcoal
import com.example.gosleep.ui.theme.SkyBlue
import com.example.gosleep.ui.theme.Navy
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
fun GoSleepScreen(
    viewModel: GoSleepViewModel,
    onRefresh: () -> Unit
)
{
    var screen by remember { mutableStateOf(TopScreen.Dashboard) }

    val screenBackground = if (isSystemInDarkTheme()) Charcoal else White
    val bubbleBackground = if (isSystemInDarkTheme()) Navy else SkyBlue

    Box(modifier = Modifier.fillMaxSize()) {

        val modifier: Modifier = Modifier
            .padding(12.dp)
            .background(bubbleBackground, RoundedCornerShape(16.dp))

        when (screen)
        {
            TopScreen.Dashboard -> Dashboard(viewModel = viewModel, screenBackground, modifier = modifier)
            TopScreen.Context_Logic -> Context_Logic(screenBackground,modifier)
            TopScreen.Privacy_By_Design -> Privacy_By_Design(screenBackground,modifier)
            TopScreen.Settings -> Settings(viewModel = viewModel, screenBackground, modifier = modifier)
        }

        screen = navigationBarLayout(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}