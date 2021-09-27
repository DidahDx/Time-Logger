package com.github.didahdx.timelogger.presentation.report_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.didahdx.timelogger.common.Resource
import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.domain.use_case.get_all_reports.GetAllReportsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Daniel Didah on 9/26/21.
 */
class ReportViewModel @Inject constructor(
    private val allReportsUseCase: GetAllReportsUseCase
) : ViewModel() {
    val state = MutableLiveData<Resource<List<LogDto>>>()

    init {
        getAllReport()
    }

    private fun getAllReport() {
        allReportsUseCase.invoke().onEach { result ->
            state.value = result
        }.launchIn(viewModelScope)
    }
}