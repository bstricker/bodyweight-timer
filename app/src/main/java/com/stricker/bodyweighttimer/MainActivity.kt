package com.stricker.bodyweighttimer

import android.os.Bundle
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.initSize
import com.stricker.bodyweighttimer.ui.theme.BodyweightTimerTheme
import com.stricker.bodyweighttimer.viewmodel.TimerViewModel
import timber.log.Timber
import java.time.Duration

class MainActivity : ComponentActivity() {

    val timerViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        initSize()

        setContent {
            BodyweightTimerTheme {

                MyApp(timerViewModel)

            }
        }
    }
}

@Composable
fun MyApp(viewModel: TimerViewModel) {

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            content = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    SetCounter(
                        viewModel.setCounter,
                        onDecrSet = viewModel::onDecrSet,
                        onIncrSet = viewModel::onIncrSet
                    )
                    Timer(viewModel.currentDuration, viewModel.timerState)
                    Spacer(modifier = Modifier.height(8.dp))
                    ModeToggle(
                        viewModel.ladderMode,
                        onModeClicked = viewModel::ladderMode::set,
                    )
                }
            },
            bottomBar = {
                ButtonBar(
                    viewModel.timerState,
                    onBeginSet = viewModel::onBeginSet,
                    onFinishSet = viewModel::onFinishSet,
                    onStop = viewModel::onStop,
                    onResetSet = viewModel::onResetSet,
                )
            }

        )
    }
}

@Preview
@Composable
fun MyAppPreview() {
    MyApp(TimerViewModel())
}

@Composable
fun SetCounter(setCounter: Int, onDecrSet: () -> Unit, onIncrSet: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            "Set",
            color = colorResource(id = R.color.purple_200),
            fontSize = 24.sp
        )


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onDecrSet, enabled = setCounter > 1) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
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
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Increase current set"
                )
            }

        }


    }
}

@Composable
fun ModeToggle(
    mode: LadderMode = LadderMode.UP,
    onModeClicked: (LadderMode) -> Unit,
) {

    Column(modifier = Modifier.width(0.7.dw)) {


        Text(
            "Mode",
            color = colorResource(id = R.color.purple_200),
        )

        Row {
            OutlinedButton(
                onClick = { onModeClicked(LadderMode.UP) },
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 0.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 0.dp
                ),
                modifier = Modifier
                    .weight(0.3f)
                    .height(60.dp),
                colors = if (mode == LadderMode.UP) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colors.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.primary
                    )
                },
//                enabled = mode != LadderMode.UP,
            ) {
                Icon(
                    imageVector = Icons.Filled.TrendingUp,
                    contentDescription = "Set training mode UP"
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "UP")
            }

            OutlinedButton(
                onClick = { onModeClicked(LadderMode.DOWN) },
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 8.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 8.dp
                ),
                modifier = Modifier
                    .weight(0.3f)
                    .height(60.dp),
                colors = if (mode == LadderMode.DOWN) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colors.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.primary
                    )
                },
//                enabled = mode != LadderMode.DOWN,
            ) {
                Icon(
                    imageVector = Icons.Filled.TrendingDown,
                    contentDescription = "Set training mode DOWN"
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "DOWN")
            }
        }
    }
}

data class ButtonItem(
    val text: String,
    val icon: ImageVector? = null,
    val onItemClicked: () -> Unit,

    )

@Composable
fun Timer(duration: Duration, state: TimerState) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {


        Spacer(modifier = Modifier.width(20.dp))


        Text(
            text = DateUtils.formatElapsedTime(duration.toSeconds()),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
        )

        when (state) {
            TimerState.TIMER -> {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = "Timer mode",
                    modifier = Modifier.width(20.dp),
                    tint = Color(0xFFE53935),
                )
            }
            TimerState.COUNTDOWN -> {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    contentDescription = "Countdown mode",
                    modifier = Modifier.width(20.dp),
                    tint = Color(0xFF43A047),
                )
            }
            else -> {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }


    }


}


@Composable
fun ButtonBar(
    state: TimerState,
    onBeginSet: () -> Unit,
    onFinishSet: () -> Unit,
    onStop: () -> Unit,
    onResetSet: () -> Unit,
) {

    Surface(
        elevation = 7.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            when (state) {
                TimerState.STOPPED -> {
                    WorkoutButton("Begin", onClick = onBeginSet, Modifier.weight(0.7f))
                    Spacer(modifier = Modifier.width(4.dp))
                    WorkoutButton("Reset", onClick = onResetSet, Modifier.weight(0.3f))
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
fun WorkoutButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.height(80.dp),
        onClick = onClick
    ) {
        Text(text)
    }

}


enum class TimerState {
    STOPPED, TIMER, COUNTDOWN
}

enum class LadderMode {
    UP, DOWN
}

enum class RestMode {
    FULL, HALF
}
