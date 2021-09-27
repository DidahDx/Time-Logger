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
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    val clockTimeInterval = Observable.interval(1, TimeUnit.SECONDS)
    var hour = 12
    var minute = 0
    var second = 0

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

}