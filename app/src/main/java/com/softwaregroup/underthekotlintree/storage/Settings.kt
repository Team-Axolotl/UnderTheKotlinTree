package com.softwaregroup.underthekotlintree.storage

import okhttp3.HttpUrl

const val DEFAULT_LANGUAGE = "en"

// todo - better lang selection pls. ffs.
var language: String = DEFAULT_LANGUAGE // default to English

var baseUrl: HttpUrl = HttpUrl.Builder().scheme("https").host("google.com").build()