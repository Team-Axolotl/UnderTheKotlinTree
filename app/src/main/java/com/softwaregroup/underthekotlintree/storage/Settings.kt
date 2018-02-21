package com.softwaregroup.underthekotlintree.storage

import okhttp3.HttpUrl

// todo - better lang selection pls. ffs.
enum class Language{
    EN, FR, BG, SW, ZH
}

var language: Language = Language.EN // default to English

// todo - denullify
var baseUrl: HttpUrl? = null