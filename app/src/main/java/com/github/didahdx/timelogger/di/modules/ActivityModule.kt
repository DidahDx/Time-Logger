package com.github.didahdx.timelogger.di.modules

import com.github.didahdx.timelogger.presentation.clock_screen.MainActivity
import com.github.didahdx.timelogger.presentation.report_screen.ReportActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Daniel Didah on 9/25/21.
 */
@Module
abstract class ActivityModule {
    /**
     * This allows us to inject things into Activities using AndroidInjection.inject(this)
     * in the onCreate() method.
     */
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesReportActivity(): ReportActivity
}