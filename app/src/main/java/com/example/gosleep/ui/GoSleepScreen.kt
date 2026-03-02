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
import com.example.gosleep.ui.theme.PurpleGrey80

@Composable
fun GoSleepScreen(
    viewModel: GoSleepViewModel,
    onRefresh: () -> Unit
)
{
    var screen by remember { mutableStateOf(TopScreen.Dashboard) }

    Box(modifier = Modifier.fillMaxSize()) {

        val modifier: Modifier = Modifier
            .padding(12.dp)
            .background(PurpleGrey80, RoundedCornerShape(16.dp))

        when (screen)
        {
            TopScreen.Dashboard -> Dashboard(viewModel = viewModel, modifier = modifier)
            TopScreen.Context_Logic -> Context_Logic(modifier)
            TopScreen.Privacy_By_Design -> Privacy_By_Design(modifier)
            TopScreen.Settings -> Settings(viewModel = viewModel, modifier = modifier)
        }

        screen = navigationBarLayout(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}