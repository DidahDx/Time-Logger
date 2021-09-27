package com.github.didahdx.timelogger.presentation.report_screen

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Daniel Didah on 9/27/21.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ReportActivityTest{

    @Test
    fun appLaunchesWithoutCrash() {
        ActivityScenario.launch(ReportActivity::class.java)
    }
}