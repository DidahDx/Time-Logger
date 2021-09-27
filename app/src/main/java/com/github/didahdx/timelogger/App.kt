package com.github.didahdx.timelogger

import android.app.Application
import androidx.annotation.Nullable
import com.github.didahdx.timelogger.di.components.DaggerAppComponent
import com.github.didahdx.timelogger.di.modules.ApiModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import javax.inject.Inject


class App: Application(),HasAndroidInjector {

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(5) // Set methodOffset to 5 in order to hide internal method calls
            .tag("")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Timber.plant(object : Timber.DebugTree() {
             override fun log(
                priority: Int,
                tag: String?,
                @NotNull message: String,
                t: Throwable?
            ) {
                Logger.log(priority, "-$tag", message, t)
            }

            @Nullable
            override fun createStackElementTag(@NotNull element: StackTraceElement): String? {
                return String.format(
                    "Class:%s: Line: %s, Method: %s",
                    super.createStackElementTag(element),
                    element.lineNumber,
                    element.methodName
                )
            }
        })

        DaggerAppComponent.builder().apiModule(ApiModule())
            .application(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }


}