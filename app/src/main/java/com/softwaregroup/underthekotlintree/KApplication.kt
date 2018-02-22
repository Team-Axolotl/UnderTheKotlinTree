package com.softwaregroup.underthekotlintree

import android.app.Application
import android.content.Context
import com.softwaregroup.underthekotlintree.storage.DEFAULT_LANGUAGE
import com.softwaregroup.underthekotlintree.storage.SHARED_PREFS_SETTINGS
import com.softwaregroup.underthekotlintree.storage.baseUrl
import com.softwaregroup.underthekotlintree.storage.language
import okhttp3.HttpUrl

private const val SH_PREFS_KEY_BASE_URL = "baseUrl"
private const val SH_PREFS_LANG = "lang"
private const val SH_PREFS_JWT = "jwt"
private const val SH_PREFS_XSRF = "xsrf"


class KApplication : Application(){

    companion object {
        var accessibleInstance: KApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        accessibleInstance = this

        val shPrefs = getSharedPreferences(SHARED_PREFS_SETTINGS, MODE_PRIVATE)
        if (shPrefs.contains(SH_PREFS_KEY_BASE_URL))
            baseUrl = HttpUrl.parse(shPrefs.getString(SH_PREFS_KEY_BASE_URL, ""))!!
        if (shPrefs.contains(SH_PREFS_LANG))
            language = shPrefs.getString(language, DEFAULT_LANGUAGE)
    }


    fun saveBaseUrl(){
        getSharedPreferences(SHARED_PREFS_SETTINGS, Context.MODE_PRIVATE).edit().putString(SH_PREFS_KEY_BASE_URL, baseUrl.toString()).apply()
    }
}