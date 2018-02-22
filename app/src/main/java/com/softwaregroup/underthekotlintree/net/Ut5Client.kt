package com.softwaregroup.underthekotlintree.net

import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.storage.getCookie
import com.softwaregroup.underthekotlintree.storage.jwt
import com.softwaregroup.underthekotlintree.storage.xsrf
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

enum class ErrorCode(val code: Int, val messageStringId: Int) {
//     UNKNOWN(Integer.MIN_VALUE)
//     NETWORK_ISSUE ( 400,)
//     CONNECTION_TIME_OUT ( 100,)
//     NO_INTERNET_CONNECTIVITY ( 101,)
//     NOT_AUTHORIZED ( 401,)
//     JSON_EXCEPTION ( "JsonException")
//     IO_EXCEPTION ( "IOException")
//     CONFIG_EXCEPTION ( "ConfigException")

    SOCKET_TIMEOUT(408, R.string.error_message_connection_time_out),
    CONNECT_FAIL(418, R.string.error_message_no_connection)
}

class Ut5Client : OkHttpClient()

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        chain!! // assert that [chain] is non-null

        //if jwt and/or xsrf are not initialized -> DOUN'T TOUCH MAH COOKI! *rabble-rabble*
        if (jwt == null || xsrf == null) return chain.proceed(chain.request())


        var cookie: String = chain.request()?.header("Cookie") ?: ""
        if (cookie.isBlank()) cookie = getCookie()

        val request = chain.request().newBuilder()
                .header("Cookie", cookie)
                .build()

        return chain.proceed(request)
    }
}