package com.softwaregroup.underthekotlintree.storage

import com.softwaregroup.underthekotlintree.KApplication
import com.softwaregroup.underthekotlintree.model.Jwt
import com.softwaregroup.underthekotlintree.model.LoginPerson
import com.softwaregroup.underthekotlintree.model.Xsrf
import com.softwaregroup.underthekotlintree.net.UT5_SERVICE
import okhttp3.HttpUrl

const val DEFAULT_LANGUAGE = "en"

/** Values that need to be stored in-memory during runtime, and should be globally accessible he here.*/

var loggedInPerson: LoginPerson? = null
var jwt: Jwt? = KApplication.accessibleInstance!!.getSavedJwtOrNull()
    set(value) {
        field = value
        KApplication.accessibleInstance!!.saveJwt()
    }

var xsrf: Xsrf? = KApplication.accessibleInstance!!.getSavedXsrfOrNull()
    set(value) {
        field = value
        KApplication.accessibleInstance!!.saveXsrf()
    }

fun getCookie(): String = "ut5-cookie=${jwt?.value}; xsrf-token=${xsrf?.uuId}"


// Settings / user preferences specific values V
var language: String = DEFAULT_LANGUAGE // default to English
var baseUrl: HttpUrl = HttpUrl.Builder().scheme("https").host("google.com").build()
    set(value){
        field = value
        UT5_SERVICE
        KApplication.accessibleInstance?.saveBaseUrl()
    }


fun clearMemStorage() {
//    jwt = null
//    xsrf = null
    loggedInPerson = null
}