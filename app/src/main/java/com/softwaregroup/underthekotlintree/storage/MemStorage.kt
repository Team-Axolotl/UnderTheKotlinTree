package com.softwaregroup.underthekotlintree.storage

import com.softwaregroup.underthekotlintree.model.Jwt
import com.softwaregroup.underthekotlintree.model.LoginPerson
import com.softwaregroup.underthekotlintree.model.Xsrf
import okhttp3.HttpUrl

/** Values that need to be stored in-memory during runtime, and should be globally accessible he here.*/

var loggedInPerson: LoginPerson? = null
var jwt: Jwt? = null
var xsrf: Xsrf? = null

fun getCookie(): String = "ut5-cookie=${jwt?.value}; ${xsrf?.uuId}"


// Settings / user preferences specific values V
const val DEFAULT_LANGUAGE = "en"
var language: String = DEFAULT_LANGUAGE // default to English
var baseUrl: HttpUrl = HttpUrl.Builder().scheme("https").host("google.com").build()