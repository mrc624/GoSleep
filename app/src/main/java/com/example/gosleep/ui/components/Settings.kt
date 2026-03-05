package com.example.gosleep.ui.components

import android.app.TimePickerDialog
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gosleep.viewmodels.GoSleepViewModel
import com.example.gosleep.ui.theme.White
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import java.sql.Time
import java.time.LocalTime

@Composable
fun Settings(viewModel: GoSleepViewModel, screenBackground: androidx.compose.ui.graphics.Color, modifier: Modifier)
{
    val notificationsStart by viewModel.notificationsStart.collectAsState()
    val notificationsEnd by viewModel.notificationsEnd.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
    ) {
        Text(
            text = "Settings",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        DisplaySlider(viewModel = viewModel, modifier = modifier)

        DisplaySleepHours(viewModel = viewModel, modifier = modifier)

        DisplayReadyTime(viewModel = viewModel, modifier = modifier)

        NotificationHoursSection(
            start = notificationsStart,
            end = notificationsEnd,
            onStartChange = { viewModel.submitNotificationsStart(it) },
            onEndChange = { viewModel.submitNotificationsEnd(it) },
            modifier
        )

    }
}

@Composable
fun DisplaySlider(viewModel: GoSleepViewModel, modifier: Modifier)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        val isOn by viewModel.notificationsEnabled.collectAsState()

        Text(
            text = "Notifications",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = isOn,
            onCheckedChange = { newValue ->
                viewModel.toggleNotifications(newValue) // 1 = ON, 0 = OFF
            }
        )
    }
}

@Composable
fun DisplaySleepHours(viewModel: GoSleepViewModel, modifier: Modifier)
{
    var sleepTimeText by remember { mutableStateOf(viewModel.sleepHours.value.toString()) }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
    {
        Text(
            text = "Preferred Sleep Time (Hours)"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            TextField(
                value = sleepTimeText,
                onValueChange =  {newText ->
                    sleepTimeText = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(75.dp),
            )

            Button(
                onClick = {
                    val time = sleepTimeText.toFloatOrNull()
                    if (time != null)
                    {
                        viewModel.submitPrefSleepTime(time)
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun DisplayReadyTime(viewModel: GoSleepViewModel, modifier: Modifier)
{
    var readyTimeText by remember { mutableStateOf(viewModel.readyTime.value.toString()) }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
    {
        Text(
            text = "Time to Get Ready (Hours)"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            TextField(
                value = readyTimeText,
                onValueChange =  {newText ->
                    readyTimeText = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .width(100.dp)
                    .height(75.dp),
            )

            Button(
                onClick = {
                    val time = readyTimeText.toFloatOrNull()
                    if (time != null)
                    {
                        viewModel.submitReadyTime(time)
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun NotificationHoursSection(
    start: LocalTime,
    end: LocalTime,
    onStartChange: (LocalTime) -> Unit,
    onEndChange: (LocalTime) -> Unit,
    modifier: Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ){
        Text("Quiet Hours", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(10.dp))

        TimePickerRow(
            label = "Start",
            time = start,
            onTimeSelected = onStartChange,
            modifier
        )

        Spacer(modifier = Modifier.height(10.dp))

        TimePickerRow(
            label = "End",
            time = end,
            onTimeSelected = onEndChange,
            modifier
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerRow(
    label: String,
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier
) {
    var showPicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$label: ${time.toString()}", fontSize = 16.sp)

        Button(onClick = { showPicker = true }) {
            Text("Change")
        }
    }

    if (showPicker) {
        TimePickerDialog(
            initialTime = time,
            onDismiss = { showPicker = false },
            onConfirm = {
                onTimeSelected(it)
                showPicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit
) {
    val state = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = false
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selected = LocalTime.of(state.hour, state.minute)
                onConfirm(selected)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(state = state)
        }
    )
}
