package com.softwaregroup.underthekotlintree.net

import retrofit2.Call

/**
 *
 */
data class HttpCallResponse<T> private constructor(
        val startingCall: Call<T>,
        val errorCode: ErrorCode? = null,
        val exception: Throwable? = null,
        val result: T? = null,
        val isSuccess: Boolean
) {
    companion object {
        fun <T> success(result: T, call: Call<T>): HttpCallResponse<T> {
            return HttpCallResponse(startingCall = call, result = result, isSuccess = true)
        }

        fun <T> error(errorCode: ErrorCode, exception: Throwable, call: Call<T>): HttpCallResponse<T> {
            return HttpCallResponse(startingCall = call, exception = exception, isSuccess = false, errorCode = errorCode)
        }
    }
}