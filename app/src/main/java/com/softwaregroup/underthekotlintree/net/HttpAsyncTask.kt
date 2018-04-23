package com.softwaregroup.underthekotlintree.net

import android.os.AsyncTask
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException

/**
 * [AsyncTask] implementation for making http requests on a separate thread.
 *  The <T> generic type is used to determine the result's Type.
 *
 * @param onResult callback function which executes [onPostExecute]. Used to receive the result of the HttpAsyncTask.
 */
class HttpAsyncTask<T>(private inline val onResult: (HttpCallResponse<T>) -> Unit) : AsyncTask<Call<T>, Unit, HttpCallResponse<T>>() {

    // todo - add method logging
    override fun doInBackground(vararg calls: Call<T>): HttpCallResponse<T> {
        // todo - handle varargs and nullability

        val call = calls[0]
        var response: Response<T>? = null

        return try {
            response = call.execute()
            HttpCallResponse.success(response.body()!!, call)
        } catch (ex: Throwable){
            val errorCode = when(ex){
                is RpcException -> ErrorCode.RPC_FAIL
                is ConnectException -> ErrorCode.CONNECT_FAIL
                is SocketTimeoutException -> ErrorCode.SOCKET_TIMEOUT
                is NoRouteToHostException -> ErrorCode.NO_ROUT
                is KotlinNullPointerException -> {
                    if (response!!.code() == ErrorCode.BAD_REQUEST.code) ErrorCode.BAD_REQUEST
                    else throw ex
                }
                else -> throw ex
            }

            HttpCallResponse.error(errorCode, ex, call)
        }
    }

    override fun onPostExecute(result: HttpCallResponse<T>) {
        onResult(result)
    }
}