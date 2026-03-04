package com.example.gosleep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gosleep.ui.theme.White

@Composable
fun Context_Logic(screenBackground: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
    ) {
        Text(
            text = "Context Logic",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}