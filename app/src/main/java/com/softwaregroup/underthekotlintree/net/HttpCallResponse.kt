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
 * A successful [HttpCallResponse]'s [result] might still contain an error of its own type (e.g. invalidCredentials).
 * The [isSuccess] flag *only denotes that the call connected successfully with usable data*, not that the result is positive.
 *
 * <b>NB!:</b> errors like " 400 - Bad request" are considered failed,
 * because although the request made it to the server, it was malformed and no UT message could be retrieved!
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
            return HttpCallResponse( errorCode = errorCode, exception = exception, startingCall = call, isSuccess = false)
        }
    }
}