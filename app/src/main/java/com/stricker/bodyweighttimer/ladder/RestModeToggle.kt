package com.stricker.bodyweighttimer.ladder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.HourglassFull
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slaviboy.composeunits.dw
import com.stricker.bodyweighttimer.ui.theme.Shapes

@Composable
fun RestModeToggle(
    mode: RestMode = RestMode.FULL,
    onModeClicked: (RestMode) -> Unit,
) {

    Column(modifier = Modifier.width(0.7.dw)) {


        Text(
            "Rest",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
        )

        Row {
            OutlinedButton(
                onClick = { onModeClicked(RestMode.FULL) },
                shape = Shapes.Button.Toggle.Segmented.Left,
                modifier = Modifier
                    .weight(0.3f)
                    .height(60.dp),
                colors = if (mode == RestMode.FULL) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.HourglassFull,
                    contentDescription = "Set training mode UP"
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "Full")
            }

            OutlinedButton(
                onClick = { onModeClicked(RestMode.HALF) },
                shape = Shapes.Button.Toggle.Segmented.Right,
                modifier = Modifier
                    .weight(0.3f)
                    .height(60.dp),
                colors = if (mode == RestMode.HALF) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
//                enabled = mode != LadderMode.DOWN,
            ) {
                Icon(
                    imageVector = Icons.Filled.HourglassBottom,
                    contentDescription = "Set training mode DOWN"
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "Half")
            }
        }
    }
}

enum class RestMode {
    FULL, HALF
}