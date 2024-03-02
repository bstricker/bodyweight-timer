package com.stricker.bodyweighttimer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stricker.bodyweighttimer.ladder.LadderMode
import com.stricker.bodyweighttimer.common.TimerState
import kotlinx.coroutines.*
import timber.log.Timber
import java.time.Duration
import java.time.Instant

class TimerViewModel() : ViewModel() {


    private var beginSetTime: Instant? = null
    private var finishSetTime: Instant? = null

    var setCounter by mutableStateOf(1)
        private set

    var ladderMode by mutableStateOf(LadderMode.UP)

    var timerState by mutableStateOf(TimerState.STOPPED)
        private set

    var currentDuration: Duration by mutableStateOf(Duration.ZERO)
        private set

    private var countDownDuration = Duration.ZERO

    fun onIncrementSet() {
        setCounter += 1
    }

    fun onDecrementSet() {
        if (setCounter > 1)
            setCounter -= 1

    }

    fun onBeginSet() {

        Timber.d("onBeginSet")

        beginSetTime = Instant.now()
        timerState = TimerState.TIMER

        viewModelScope.launch {
            while (isActive && timerState == TimerState.TIMER) {
                currentDuration = Duration.between(beginSetTime, Instant.now())
                delay(100)
                Timber.d("onTick ${currentDuration}")
            }
        }

    }

    fun onFinishSet() {

        Timber.d("onFinishSet")

        finishSetTime = Instant.now()
        timerState = TimerState.COUNTDOWN

        viewModelScope.coroutineContext.cancelChildren()

        countDownDuration = currentDuration


        viewModelScope.launch {

            while (isActive && timerState == TimerState.COUNTDOWN) {
                val between = Duration.between(finishSetTime, Instant.now())
                currentDuration = countDownDuration.minus(between)
                if (currentDuration < Duration.ZERO) {
                    onStop()
                }

                delay(100)

            }

        }

    }


    private fun setCounterNextSet() {
        when (ladderMode) {
            LadderMode.DOWN -> onDecrementSet()
            LadderMode.UP -> onIncrementSet()
        }
    }

    fun onStop() {
        viewModelScope.coroutineContext.cancelChildren()
        timerState = TimerState.STOPPED
        currentDuration = Duration.ZERO
        setCounterNextSet()
    }

    fun onResetSet() {
        viewModelScope.coroutineContext.cancelChildren()
        timerState = TimerState.STOPPED
        currentDuration = Duration.ZERO
        setCounter = 1
    }

}