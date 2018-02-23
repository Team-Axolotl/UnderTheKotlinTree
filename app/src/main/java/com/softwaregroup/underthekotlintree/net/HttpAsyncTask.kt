package com.softwaregroup.underthekotlintree.net

import android.os.AsyncTask
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class HttpAsyncTask<T>(onResult: (HttpCallResponse<T>) -> Unit) : AsyncTask<Call<T>, Unit, HttpCallResponse<T>>() {
    private val callback = onResult

    override fun doInBackground(vararg calls: Call<T>): HttpCallResponse<T> {
        // todo - handle varargs and nullability

        val call = calls[0]
        var response: Response<T>? = null

        return try {
            response = call.execute()
            HttpCallResponse.success(response.body()!!, call)
        } catch (e0: ConnectException){ //todo - can be switch cased with a 'is' call to various throwable Types and an unknown errCode. For debug tho, better to crash and get fixed.
            HttpCallResponse.error(ErrorCode.CONNECT_FAIL, e0, call)
        } catch (e1: SocketTimeoutException) {
            HttpCallResponse.error(ErrorCode.SOCKET_TIMEOUT, e1, call)
        } catch (e2: KotlinNullPointerException){
            HttpCallResponse.error(ErrorCode.BAD_REQUEST, e2, call)
        } catch (e3: KotlinNullPointerException){
            if (response!!.code() == ErrorCode.BAD_REQUEST.code)
                HttpCallResponse.error(ErrorCode.BAD_REQUEST, e3, call)
            else throw e3
        }
    }

    override fun onPostExecute(result: HttpCallResponse<T>) {
        callback(result)
    }
}