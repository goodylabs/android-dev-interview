package com.goodylabs.android.interview.data

sealed class Result<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Error(val message: Throwable?) : Result<Nothing>()
}

inline fun <T : Any?> performRequest(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}
