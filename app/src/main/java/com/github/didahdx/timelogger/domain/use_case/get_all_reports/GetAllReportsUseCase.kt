package com.github.didahdx.timelogger.domain.use_case.get_all_reports

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
 * Created by Daniel Didah on 9/26/21.
 */
class GetAllReportsUseCase @Inject constructor(
    private val repository: ServersRepository
) {

    operator fun invoke(): Flow<Resource<List<LogDto>>> = flow {

        try {
            emit(Resource.Loading<List<LogDto>>())
            val result = repository.getAllReports()
            emit(Resource.Success<List<LogDto>>(result))
        } catch (e: HttpException) {
            emit(Resource.Error<List<LogDto>>( "Something went wrong"))
        } catch (io: IOException) {
            emit(Resource.Error<List<LogDto>>("Something went wrong"))
        }

    }
}