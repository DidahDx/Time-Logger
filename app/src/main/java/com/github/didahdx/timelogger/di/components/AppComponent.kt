package com.github.didahdx.timelogger.di.components

import android.app.Application
import com.github.didahdx.timelogger.App
import com.github.didahdx.timelogger.di.modules.ActivityModule
import com.github.didahdx.timelogger.di.modules.ApiModule
import com.github.didahdx.timelogger.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Daniel Didah on 9/25/21.
 */
@Component(modules = [ActivityModule::class, ApiModule::class,
    ViewModelModule::class,AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder

        fun build():AppComponent
    }

    fun inject(app: App)

}