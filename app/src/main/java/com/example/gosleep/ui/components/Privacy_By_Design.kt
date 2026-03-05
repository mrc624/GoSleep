package com.example.gosleep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gosleep.ui.theme.White

@Composable
fun Privacy_By_Design(screenBackground: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Permissions & Data Security",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        PermissionSection(
            title = "Motion Sensors (Accelerometer & Gyroscope)",
            explanation = """
                These sensors are used to detect when the user picks up the phone.
                Privacy considerations:
                * Data stays on your device
                * No raw sensor data is stored
                * The only thing being stored is the time stamp of the most recent phone pick up
            """.trimIndent(),
            modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        PermissionSection(
            title = "Calendar",
            explanation = """
                We use your calendar to estimate when you should go to sleep.
                Privacy considerations:
                * Our calendar access is read only, meaning we will never modify or delete calendar items
                * Only event times and titles are read
                * The data is only used locally
            """.trimIndent(),
            modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        PermissionSection(
            title = "Notifications",
            explanation = """
                These notifications are used to remind you when to sleep.
                Privacy considerations:
                * Notifications can be turned off at anytime
            """.trimIndent(),
            modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        PermissionSection(
            title = "General Practices",
            explanation = """
                * All recorded information is stored in a local database
                * We only collect the minimal data needed 
            """.trimIndent(),
            modifier
        )
    }
}

@Composable
fun PermissionSection(title: String, explanation: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = explanation,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp
        )
        Spacer(Modifier.height(16.dp))
    }
}