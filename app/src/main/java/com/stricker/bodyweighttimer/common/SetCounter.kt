package com.stricker.bodyweighttimer.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetCounter(setCounter: Int, onDecrSet: () -> Unit, onIncrSet: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            "Set",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary,
        )


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onDecrSet, enabled = setCounter > 1) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Decrease current set"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))


            Text(
                setCounter.toString(),
                fontSize = 48.sp,
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(onClick = onIncrSet) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Increase current set"
                )
            }

        }


    }
}