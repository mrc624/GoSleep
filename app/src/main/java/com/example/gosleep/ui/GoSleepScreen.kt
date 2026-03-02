package com.example.gosleep.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gosleep.ui.components.Context_Logic
import com.example.gosleep.ui.components.Dashboard
import com.example.gosleep.ui.components.Privacy_By_Design
import com.example.gosleep.ui.components.Settings
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.ui.components.navigationBarLayout
import com.example.gosleep.ui.components.TopScreen

@Composable
fun GoSleepScreen(
    viewModel: GoSleepViewModel,
    onRefresh: () -> Unit
)
{
    var screen by remember { mutableStateOf(TopScreen.Dashboard) }

    Box(modifier = Modifier.fillMaxSize()) {

        when (screen)
        {
            TopScreen.Dashboard -> Dashboard()
            TopScreen.Context_Logic -> Context_Logic()
            TopScreen.Privacy_By_Design -> Privacy_By_Design()
            TopScreen.Settings -> Settings()
        }

        screen = navigationBarLayout(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}