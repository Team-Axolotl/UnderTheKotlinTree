package com.softwaregroup.underthekotlintree

import android.app.Application
import android.content.Context
import com.softwaregroup.underthekotlintree.model.Jwt
import com.softwaregroup.underthekotlintree.model.Xsrf
import com.softwaregroup.underthekotlintree.storage.*
import okhttp3.HttpUrl

private const val SHARED_PREFS_SETTINGS = "shPrefs_for_settings"
private const val SH_PREFS_KEY_BASE_URL = "baseUrl"
private const val SH_PREFS_LANG = "lang"

private const val SHARED_PREFS_COOKIE = "shPrefCook"
private const val SH_PREFS_JWT = "jwt"
private const val SH_PREFS_XSRF = "xsrf"


class KApplication : Application(){

    companion object {
        var accessibleInstance: KApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        accessibleInstance = this

        //todo - This logic is dubious. It also introduces a feed-back of read from shPref -> store in mem => sore in shPref. (see relevant setters for more details)
        val settingsShPrefs = getSharedPreferences(SHARED_PREFS_SETTINGS, MODE_PRIVATE)
        if (settingsShPrefs.contains(SH_PREFS_KEY_BASE_URL))
            baseUrl = HttpUrl.parse(settingsShPrefs.getString(SH_PREFS_KEY_BASE_URL, ""))!!
        if (settingsShPrefs.contains(SH_PREFS_LANG))
            language = settingsShPrefs.getString(language, DEFAULT_LANGUAGE)

    }


    fun saveBaseUrl(){
        getSharedPreferences(SHARED_PREFS_SETTINGS, Context.MODE_PRIVATE).edit().putString(SH_PREFS_KEY_BASE_URL, baseUrl.toString()).apply()
    }


    fun saveJwt() {
        val shPrefs = getSharedPreferences(SHARED_PREFS_COOKIE, MODE_PRIVATE)
        shPrefs.edit().putString(SH_PREFS_JWT, jwt?.value).apply()
    }

    fun saveXsrf() {
        val shPrefs = getSharedPreferences(SHARED_PREFS_COOKIE, MODE_PRIVATE)
        shPrefs.edit().putString(SH_PREFS_XSRF, xsrf?.uuId).apply()
    }

    fun getSavedJwtOrNull(): Jwt? {
        val token = getSharedPreferences(SHARED_PREFS_COOKIE, MODE_PRIVATE).getString(SH_PREFS_JWT, null)
        return if (token == null) null else Jwt(token)
    }
    fun getSavedXsrfOrNull(): Xsrf? {
        val token = getSharedPreferences(SHARED_PREFS_COOKIE, MODE_PRIVATE).getString(SH_PREFS_XSRF, null)
        return if (token == null) null else Xsrf(token)
    }
}