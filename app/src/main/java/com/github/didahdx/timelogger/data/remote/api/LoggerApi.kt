package com.github.didahdx.timelogger.data.remote.api

import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Daniel Didah on 9/25/21.
 */
interface LoggerApi {
    @POST("start")
    suspend fun startServer(@Body programDto: ProgramDto): LogDto

    @POST("stop")
    suspend fun stopServer(@Body programDto: ProgramDto): LogDto

    @POST("report")
    suspend fun getReport(@Body programDto: ProgramDto): LogDto

    @GET("samplereport")
     suspend fun getAllReports(): List<LogDto>
}