package com.softwaregroup.underthekotlintree.net

import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.storage.getCookie
import com.softwaregroup.underthekotlintree.storage.jwt
import com.softwaregroup.underthekotlintree.storage.xsrf
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

/** Representation of a http error mapped to a strings.xml value for human-readable message. */
enum class ErrorCode(val code: Int, val messageStringId: Int) {
    SOCKET_TIMEOUT(408, R.string.error_message_connection_time_out),
    CONNECT_FAIL(418, R.string.error_message_no_connection),
    BAD_REQUEST(400, R.string.error_message_bad_request),
    NO_ROUT(400, R.string.error_message_no_rout_to_host)
}

/** Client configuration for the [UT5_SERVICE] */
val UT5_CLIENT: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30_000, TimeUnit.MILLISECONDS)
        .readTimeout(30_000, TimeUnit.MILLISECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

/** Request interceptor for injecting the jwt/xsrf cookie once logged-in */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        chain!! // assert that [chain] is non-null

        //if jwt and/or xsrf are not initialized -> DOUN'T TOUCH MAH COOKI! *rabble-rabble*
        if (jwt == null || xsrf == null) return chain.proceed(chain.request())

        // get old cookie or empty string (to avoid null values)
        var cookie: String = chain.request()?.header("Cookie") ?: ""

        // if the old one is empty -> replace it with [getCooke]
        if (cookie.isBlank()) cookie = getCookie()
        val request = chain.request().newBuilder()
                .header("Cookie", cookie)
                .build()

        return chain.proceed(request)
    }
}