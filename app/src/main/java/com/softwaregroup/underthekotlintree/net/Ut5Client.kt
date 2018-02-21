package com.softwaregroup.underthekotlintree.net

import com.softwaregroup.underthekotlintree.storage.getCookie
import com.softwaregroup.underthekotlintree.storage.jwt
import com.softwaregroup.underthekotlintree.storage.xsrf
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class Ut5Client: OkHttpClient()

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        chain!! // assert that [chain] is non-null

        //if jwt and/or xsrf are not initialized -> DOUN'T TOUCH MAH COOKI! *rabble-rabble*
        if (jwt == null || xsrf == null) return chain.proceed(chain.request())


        var cookie: String = chain.request()?.header("Cookie")?: ""
        if (cookie.isBlank()) cookie = getCookie()

        val request = chain.request().newBuilder()
                .header("Cookie", cookie)
                .build()

        return chain.proceed(request)
    }
}