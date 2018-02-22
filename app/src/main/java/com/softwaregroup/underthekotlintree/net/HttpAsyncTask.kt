package com.softwaregroup.underthekotlintree.net

import android.os.AsyncTask
import retrofit2.Call
import java.net.ConnectException
import java.net.SocketTimeoutException

class HttpAsyncTask<T>(onResult: (HttpCallResponse<T>) -> Unit) : AsyncTask<Call<T>, Unit, HttpCallResponse<T>>() {
    private val callback = onResult

    override fun doInBackground(vararg calls: Call<T>?): HttpCallResponse<T> {
        // todo - handle varargs and nullability

        return try {
            HttpCallResponse.success(calls[0]?.execute()?.body()!!, calls[0]!!)
        } catch (e0: ConnectException){ //todo - can be switch cased with a 'is' call to various throwable Types and an unkown errCode. For debug tho, better to crash and get fixed.
            HttpCallResponse.error(ErrorCode.CONNECT_FAIL, e0, calls[0]!!)
        } catch (e1: SocketTimeoutException) {
            HttpCallResponse.error(ErrorCode.SOCKET_TIMEOUT, e1, calls[0]!!)
        }
    }

    override fun onPostExecute(result: HttpCallResponse<T>) {
        callback(result)
    }
}