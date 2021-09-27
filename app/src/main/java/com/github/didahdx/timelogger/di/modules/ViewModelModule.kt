package com.github.didahdx.timelogger.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.didahdx.timelogger.presentation.clock_screen.MainActivityViewModel
import com.github.didahdx.timelogger.presentation.report_screen.ReportViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Daniel Didah on 9/25/21.
 */
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory

    /**
     * injects this object into a Map using the @IntoMap annotation,
     * with the  MainActivityViewModel.class as key,
     * and a Provider that will build a ViewModel
     * object.
     *
     * */
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReportViewModel::class)
    internal abstract fun bindReportViewModel(reportViewModel: ReportViewModel):ViewModel


}