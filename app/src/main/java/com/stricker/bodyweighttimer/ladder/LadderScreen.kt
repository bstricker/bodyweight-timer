package com.stricker.bodyweighttimer.ladder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stricker.bodyweighttimer.common.TimerState
import com.stricker.bodyweighttimer.common.SetCounter
import com.stricker.bodyweighttimer.common.Timer
import com.stricker.bodyweighttimer.common.WorkoutButton
import com.stricker.bodyweighttimer.ui.theme.Shapes
import com.stricker.bodyweighttimer.viewmodel.TimerViewModel

@Composable
fun ButtonBar(
    state: TimerState,
    onBeginSet: () -> Unit,
    onFinishSet: () -> Unit,
    onStop: () -> Unit,
    onResetSet: () -> Unit,
) {

    Surface(
        shadowElevation = 7.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            when (state) {
                TimerState.STOPPED -> {
                    WorkoutButton(
                        "Begin",
                        onClick = onBeginSet,
                        modifier = Modifier.weight(0.65f),
                        shape = Shapes.Button.Segmented.Left,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    WorkoutButton(
                        "Reset",
                        onClick = onResetSet,
                        modifier = Modifier.weight(0.35f),
                        shape = Shapes.Button.Segmented.Right,
                    )
                }

                TimerState.TIMER -> {
                    WorkoutButton("Finish", onClick = onFinishSet, Modifier.weight(0.5f))
                }

                TimerState.COUNTDOWN -> {
                    WorkoutButton("Stop", onClick = onStop, Modifier.weight(0.5f))
                }
            }
        }

    }
}

@Composable
fun LadderScreen(viewModel: TimerViewModel) {

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                ButtonBar(
                    viewModel.timerState,
                    onBeginSet = viewModel::onBeginSet,
                    onFinishSet = viewModel::onFinishSet,
                    onStop = viewModel::onStop,
                    onResetSet = viewModel::onResetSet,
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                SetCounter(
                    viewModel.setCounter,
                    onDecrSet = viewModel::onDecrementSet,
                    onIncrSet = viewModel::onIncrementSet
                )
                Spacer(modifier = Modifier.height(12.dp))
                Timer(viewModel.currentDuration, viewModel.timerState)
                Spacer(modifier = Modifier.height(12.dp))
                LadderModeToggle(
                    viewModel.ladderMode,
                    onModeClicked = viewModel::ladderMode::set,
                )
                Spacer(modifier = Modifier.height(8.dp))
                RestModeToggle(
                    viewModel.restMode,
                    onModeClicked = viewModel::restMode::set,
                )
            }

        }
    }
}

@Preview
@Composable
fun LadderScreenPreview() {
    LadderScreen(TimerViewModel().apply { onBeginSet() })
}