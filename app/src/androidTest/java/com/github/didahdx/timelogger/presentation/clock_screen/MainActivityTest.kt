package com.github.didahdx.timelogger.presentation.clock_screen

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Daniel Didah on 9/27/21.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Test
    fun appLaunchesWithoutCrash() {
        ActivityScenario.launch(MainActivity::class.java)
    }

}