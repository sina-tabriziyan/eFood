package com.sina.efood.data

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

typealias RootError = Error

sealed interface AppResult<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : AppResult<D, E>
    data class Error<out D, out E : RootError>(val error: E) : AppResult<D, E>
}

abstract class UseCaseFlow<PARAMS, RESPONSE> {
    operator fun invoke(
        params: PARAMS, timeout: Duration = DefaultTimeout
    ): Flow<AppResult<RESPONSE, DataError.Network>> = flow {
        try {
            withTimeout(timeout) {
                execute(params).collect { response ->
                    emit(AppResult.Success(response))
                }
            }
        } catch (e: TimeoutCancellationException) {
            emit(AppResult.Error(DataError.Network.REQUEST_TIMEOUT))
        } catch (e: Exception) {
            emit(AppResult.Error(DataError.Network.UNKNOWN))
        }
    }

    protected abstract suspend fun execute(params: PARAMS): Flow<RESPONSE>

    companion object {
        private val DefaultTimeout = 100.milliseconds
    }
}

fun <D, E : RootError> AppResult<D, E>.toFlow(): Flow<AppResult<D, E>> = flow {
    when (this@toFlow) {
        is AppResult.Success -> emit(this@toFlow)
        is AppResult.Error -> emit(this@toFlow)
    }
}
fun <D> Response<D>.openResponse(): AppResult<D, DataError.Network> {
    return if (isSuccessful) {
        val body = body()
        when {
            code() in 200..201 && body != null -> AppResult.Success(body)
            body == null -> AppResult.Error(DataError.Network.UNKNOWN)
            else -> AppResult.Error(DataError.Network.UNKNOWN)
        }
    } else {
        when (code()) {
            in 400..499 -> AppResult.Error(DataError.Network.REQUEST_TIMEOUT)
            in 500..599 -> AppResult.Error(DataError.Network.NO_INTERNET)
            else -> AppResult.Error(DataError.Network.UNKNOWN)
        }
    }
}

//fun <T> Flow<AppResult<T,*>>.safeOpen(): Flow<T> = map { response ->
//    return@map when (response) {
//        is AppResult.Error -> throw response.error
//        is AppResult.Success -> response.data
//    }
//}
