package com.example.gosleep.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

enum class TopScreen(val displayName: String) {
    Dashboard("Dashboard"),
    Context_Logic("Context Logic"),
    Privacy_By_Design("Privacy By Design"),
    Settings("Settings")
}

@Composable
fun NavigationBarLayout(modifier: Modifier = Modifier){
    var currentScreen by remember { mutableStateOf(TopScreen.Dashboard) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        TopScreen.entries.forEach { screen ->
            FilterChip(
                selected = currentScreen == screen,
                onClick = { currentScreen = screen },
                label = {
                    Text(
                        text = screen.displayName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}