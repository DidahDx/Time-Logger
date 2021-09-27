package com.github.didahdx.timelogger.data.repository

import com.github.didahdx.timelogger.data.remote.api.LoggerApi
import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import com.github.didahdx.timelogger.domain.repository.ServersRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Daniel Didah on 9/25/21.
 */
class ServersRepositoryImpl @Inject constructor(
    private val api: LoggerApi
    ) : ServersRepository {
    override suspend fun startServer(programDto: ProgramDto): LogDto {
        return api.startServer(programDto = programDto)
    }

    override suspend fun stopServer(programDto: ProgramDto): LogDto {
        return api.stopServer(programDto = programDto)
    }

    override suspend fun getReport(programDto: ProgramDto): LogDto {
        return api.getReport(programDto = programDto)
    }

    override suspend fun getAllReports(): List<LogDto> {
       return api.getAllReports()
    }
}