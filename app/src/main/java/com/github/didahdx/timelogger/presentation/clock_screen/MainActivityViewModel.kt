package com.github.didahdx.timelogger.presentation.clock_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.didahdx.timelogger.common.Resource
import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import com.github.didahdx.timelogger.domain.use_case.get_report.GetReportUseCase
import com.github.didahdx.timelogger.domain.use_case.start_servers.StartServersUseCase
import com.github.didahdx.timelogger.domain.use_case.stop_server.StopServerUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Daniel Didah on 9/25/21.
 */
class MainActivityViewModel @Inject constructor(
    private val startServersUseCase: StartServersUseCase,
    private val stopServersUseCase: StopServerUseCase,
    private val getReportUseCase: GetReportUseCase
) : ViewModel() {

    val state = MutableLiveData<Resource<LogDto>>()
    val timer = MutableLiveData<Long>()
   private val compositeDisposable= CompositeDisposable()

    var hour = 12
    var minute = 0
    var second = 0
    init {
        getTimer()
    }


    private fun getTimer(){
        compositeDisposable += Observable.interval(1, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .take(150001)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                   timer.value =it
            }, Timber::e)
    }

    fun startServer(programDto: ProgramDto) {
        startServersUseCase.invoke(programDto).onEach { result ->
            state.value = result
        }.launchIn(viewModelScope)
    }

    fun stopServer(programDto: ProgramDto) {
        stopServersUseCase.invoke(programDto).onEach { results ->
            state.value = results
        }.launchIn(viewModelScope)
    }

    fun getReport(programDto: ProgramDto) {
        getReportUseCase.invoke(programDto).onEach { result ->
            state.value = result
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}