package com.stricker.bodyweighttimer

import android.os.Bundle
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.initSize
import com.stricker.bodyweighttimer.ui.theme.AppTheme
import com.stricker.bodyweighttimer.ui.theme.color_icon_timer_down
import com.stricker.bodyweighttimer.ui.theme.color_icon_timer_up
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
            AppTheme {

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
                Timer(viewModel.currentDuration, viewModel.timerState)
                Spacer(modifier = Modifier.height(8.dp))
                ModeToggle(
                    viewModel.ladderMode,
                    onModeClicked = viewModel::ladderMode::set,
                )
            }

        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    MyApp(TimerViewModel().apply { onBeginSet() })
}

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

@Composable
fun ModeToggle(
    mode: LadderMode = LadderMode.UP,
    onModeClicked: (LadderMode) -> Unit,
) {

    Column(modifier = Modifier.width(0.7.dw)) {


        Text(
            "Mode",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
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
//                enabled = mode != LadderMode.UP,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
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
                    imageVector = Icons.AutoMirrored.Filled.TrendingDown,
                    contentDescription = "Set training mode DOWN"
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "DOWN")
            }
        }
    }
}

@Composable
fun Timer(duration: Duration, state: TimerState) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {


        // offset for timer icon (up/down arrow)
        Spacer(modifier = Modifier.width(30.dp))

        Text(
            text = DateUtils.formatElapsedTime(duration.seconds),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
        )

        when (state) {
            TimerState.TIMER -> {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = "Timer mode",
                    modifier = Modifier.size(30.dp),
                    tint = color_icon_timer_up,
                )
            }

            TimerState.COUNTDOWN -> {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    contentDescription = "Countdown mode",
                    modifier = Modifier.size(30.dp),
                    tint = color_icon_timer_down,
                )
            }

            else -> {
                Spacer(modifier = Modifier.width(30.dp))
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
        onClick = onClick,
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
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
