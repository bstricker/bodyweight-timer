package com.stricker.bodyweighttimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.slaviboy.composeunits.initSize
import com.stricker.bodyweighttimer.ladder.LadderScreen
import com.stricker.bodyweighttimer.ui.theme.AppTheme
import com.stricker.bodyweighttimer.viewmodel.TimerViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val timerViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        Timber.plant(Timber.DebugTree())

        initSize()

        setContent {
            AppTheme {

                LadderScreen(timerViewModel)

            }
        }
    }
}