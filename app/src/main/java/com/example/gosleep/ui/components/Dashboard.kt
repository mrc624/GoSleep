package com.example.gosleep.ui.components

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gosleep.data.Event
import com.example.gosleep.viewmodels.GoSleepViewModel
import java.util.Locale
import com.example.gosleep.ui.theme.White

@Composable
fun Dashboard(viewModel: GoSleepViewModel, screenBackground: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = screenBackground)
    ) {
        Text(
            text = "Home",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        DisplayNextEvent(viewModel.getNextEvent(), modifier)

        val firstMorningEvent = viewModel.getFirstMorningEvent(viewModel.getMorningStart())

        DisplayFirstMorningEvent(firstMorningEvent, viewModel.getSleepHours(firstMorningEvent), modifier)

    }
}

@Composable
fun DisplayNextEvent(event: Event?, modifier: Modifier)
{
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(6.dp)
    )
    {
        Text(
            text = "Your Next Event",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )

        if (event != null)
        {
            Text(
                text = "Event Name: " + event.name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Start Time: " + event.startTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "End Time: " + event.endTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
        else
        {
            Text(
                text = "No events for the next 48 hours!",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DisplayFirstMorningEvent(event: Event?, sleepHours: Float, modifier: Modifier)
{
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(6.dp)
    )
    {
        Text(
            text = "Your Next Morning Event",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )

        if (event != null)
        {
            Text(
                text = "Event Name: " + event.name,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Start Time: " + event.startTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "End Time: " + event.endTime,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )

            val message = String.format(Locale.US, "If you went to sleep now you'd get: %.1f hours", sleepHours)

            Text(
                text = message,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
        else
        {
            Text(
                text = "No events tomorrow morning! Sleep in!",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
    }
}