package com.softwaregroup.underthekotlintree.storage

import com.softwaregroup.underthekotlintree.model.Jwt
import com.softwaregroup.underthekotlintree.model.LoginPerson
import com.softwaregroup.underthekotlintree.model.Xsrf


var loggedInPerson: LoginPerson? = null
var jwt: Jwt? = null
var xsrf: Xsrf? = null

fun getCookie(): String = "ut5-cookie=${jwt?.value}; ${xsrf?.uuId}"