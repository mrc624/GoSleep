package com.example.gosleep.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.ui.components.NavigationBarLayout

@Composable
fun GoSleepScreen(
    viewModel: GoSleepViewModel,
    onRefresh: () -> Unit
)
{
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

        }

        NavigationBarLayout(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}