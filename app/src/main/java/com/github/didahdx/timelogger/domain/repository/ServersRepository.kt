package com.github.didahdx.timelogger.domain.repository

import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Daniel Didah on 9/25/21.
 */
interface ServersRepository {
    suspend fun startServer(programDto: ProgramDto): LogDto
    suspend fun stopServer(programDto: ProgramDto): LogDto
    suspend fun getReport(programDto: ProgramDto): LogDto
    suspend fun getAllReports(): List<LogDto>
}