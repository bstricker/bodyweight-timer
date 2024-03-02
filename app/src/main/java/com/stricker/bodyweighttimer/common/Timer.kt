package com.stricker.bodyweighttimer.common

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stricker.bodyweighttimer.ui.theme.color_icon_timer_down
import com.stricker.bodyweighttimer.ui.theme.color_icon_timer_up
import java.time.Duration

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

enum class TimerState {
    STOPPED, TIMER, COUNTDOWN
}