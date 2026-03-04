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
fun Context_Logic(screenBackground: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Context Logic",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text =
                """
                    Your phone's motion sensors (accelerometer & gyroscope) are used to detect when you pick up your phone
                        * The accelerometer measures linear acceleration and detects sudden changes in motion
                        * The gyroscope measures rotational velocity and detects if the phone is turned
                    When both sensors detect changes in motion above a designated threshold, it is recorded as the user picking up their phone
                    The virtual sensor, Calendar, reads the users calendar to determine when their first event of the following day is.
                    If they pick up the phone within a certain time period of their first event of the following day, they will be sent a notification 
                    telling them to go to sleep. User preferences, including how much sleep they would like to get, if notifications are turned on, and
                    how long they need in the morning to get ready are saved in a room database. The last time they picked up the phone is also stored.        
                """.trimIndent(),
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp
        )

    }
}