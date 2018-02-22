package com.softwaregroup.underthekotlintree.net

import retrofit2.Call

/**
 * Wrapper class for the http Call/Response mechanism.
 * Allows for sending (yet another) layer of error handling/logging (and some other stuff).
 *
 * Get instances via the [success] and [error] constructing methods, for successful and failed requests respectively.
 *
 * Stores a [Call] and its result.
 * The result is either a success and a <[T]>,
 * or an error with [ErrorCode] and [Throwable] cause.
 *
 * A successful [HttpCallResponse]'s [result] might still be an error of its own type (e.g. invalidCredentials).
 * The [isSuccess] flag *only denotes that the call connected successfully*, not that the result is positive.
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