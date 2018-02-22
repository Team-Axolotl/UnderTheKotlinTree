package com.softwaregroup.underthekotlintree.storage

import com.softwaregroup.underthekotlintree.KApplication
import com.softwaregroup.underthekotlintree.model.Jwt
import com.softwaregroup.underthekotlintree.model.LoginPerson
import com.softwaregroup.underthekotlintree.model.Xsrf
import okhttp3.HttpUrl

const val SHARED_PREFS_SETTINGS = "shPrefs_for_settings"
const val DEFAULT_LANGUAGE = "en"

/** Values that need to be stored in-memory during runtime, and should be globally accessible he here.*/

var loggedInPerson: LoginPerson? = null
var jwt: Jwt? = null
var xsrf: Xsrf? = null

fun getCookie(): String = "ut5-cookie=${jwt?.value}; ${xsrf?.uuId}"


// Settings / user preferences specific values V
var language: String = DEFAULT_LANGUAGE // default to English
var baseUrl: HttpUrl = HttpUrl.Builder().scheme("https").host("google.com").build()
    set(value){
        field = value
        KApplication.accessibleInstance?.saveBaseUrl()
    }