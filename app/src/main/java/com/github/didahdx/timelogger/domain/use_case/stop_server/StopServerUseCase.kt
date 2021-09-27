package com.github.didahdx.timelogger.domain.use_case.stop_server

import com.github.didahdx.timelogger.common.Resource
import com.github.didahdx.timelogger.data.remote.dto.LogDto
import com.github.didahdx.timelogger.data.remote.dto.ProgramDto
import com.github.didahdx.timelogger.domain.repository.ServersRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Daniel Didah on 9/25/21.
 */
class StopServerUseCase @Inject constructor(
    private val repository: ServersRepository
) {

    operator fun invoke(programDto: ProgramDto): Flow<Resource<LogDto>> = flow {
        try {
           emit(Resource.Loading<LogDto>())
           val results= repository.stopServer(programDto = programDto)
            emit(Resource.Success<LogDto>(results))
        } catch (e: HttpException) {
           emit(Resource.Error<LogDto>( "Something went wrong"))
        } catch (io: IOException) {
            emit(Resource.Error<LogDto>("Something went wrong"))
        }
    }
}