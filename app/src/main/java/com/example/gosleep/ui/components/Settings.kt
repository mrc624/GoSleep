package com.example.gosleep.ui.components

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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

@Composable
fun Settings(viewModel: GoSleepViewModel, modifier: Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
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
        var isOn by remember { mutableStateOf(viewModel.getNotifications()) }

        Text(
            text = "Notifications",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = isOn,
            onCheckedChange = { newValue ->
                isOn = newValue
                viewModel.toggleNotifications(isOn) // 1 = ON, 0 = OFF
            }
        )
    }
}

@Composable
fun DisplaySleepHours(viewModel: GoSleepViewModel, modifier: Modifier)
{
    var sleepTimeText by remember { mutableStateOf(viewModel.getPrefSleepTime().toString()) }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    )
    {
        Text(
            text = "Preferred Sleep Time"
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
                        sleepTimeText = viewModel.getPrefSleepTime().toString()
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}