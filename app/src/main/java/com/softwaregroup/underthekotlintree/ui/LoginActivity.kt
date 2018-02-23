package com.softwaregroup.underthekotlintree.ui

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.storage.*
import com.softwaregroup.underthekotlintree.util.startActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * First Activity that the User sees when opening the app.
 * Handles logging in via authentication with credentials to a server as set in [SettingsActivity]
 */
class LoginActivity : BaseActivity(), OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            loginButton -> if (validateLoginInput()) beginLogin()
            loginSettingsButton -> startActivity(SettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener(this)
        loginSettingsButton.setOnClickListener(this)

        if (jwt != null && xsrf != null) {
            beginLogin(isSilentLogin = true)
            Log.wtf("TAAAG", "${jwt!!.value} : ${xsrf!!.uuId}")
        }
    }

    /** Unwrap the [LoginData] from withing the [response]. Show and error message and return null if the request was not successful*/
    private fun processLoginResponse(response: HttpCallResponse<JsonRpcResponse<LoginData>>): LoginData? {
        return if (response.isSuccess && response.result!!.error == null) {
            response.result.result
        } else {
            showErrorMessage(when (response.isSuccess) {
                true -> response.result!!.error!!.message
                false -> getString(response.errorCode!!.messageStringId)
            })

            null
        }
    }

    private fun showLoginLoad() {
        loginProgress.visibility = VISIBLE
        loginButton.isEnabled = false
        loginNameInput.isEnabled = false
        loginPasswordInput.isEnabled = false
    }

    private fun hideLoginLoad() {
        loginProgress.visibility = INVISIBLE
        loginButton.isEnabled = true
        loginNameInput.isEnabled = true
        loginPasswordInput.isEnabled = true
    }

    /** Generate a [JsonRpcRequest] for the [Ut5Service.login] */
    private fun getLoginRequest(isForSilentLogin: Boolean = false): JsonRpcRequest {
        return when (isForSilentLogin) {
            false -> JsonRpcRequest(
                    method = REQUEST_IDENTITY_CHECK,
                    params = mapOf(
                            "username" to loginNameInput.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                            "password" to loginPasswordInput.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                            "timezone" to TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT),
                            "channel" to "mobile"
                    ))
            true -> JsonRpcRequest(method = REQUEST_IDENTITY_CHECK, params = mapOf("channel" to "mobile"))
        }
    }

    private fun validateLoginInput(): Boolean {
        return when {
            loginNameInput.text.isBlank() -> {
                loginNameInput.error = getString(R.string.error_invalid_input)
                loginNameInput.requestFocus()
                false
            }

            loginPasswordInput.text.isBlank() -> {
                loginPasswordInput.error = getString(R.string.error_invalid_input)
                loginPasswordInput.requestFocus()
                false
            }

            else -> true
        }
    }

    private fun beginLogin(isSilentLogin: Boolean = false) {
        showLoginLoad()

//        val start0 = System.currentTimeMillis()
//        UT5_SERVICE
//        toast("0: " + (System.currentTimeMillis() - start0))
//
//        val start1 = System.currentTimeMillis()
//        UT5_SERVICE.login(getLoginRequest())
//        toast("1: " + (System.currentTimeMillis() - start1))


        // Create an AsyncTask for executing login request.
        val task = HttpAsyncTask<JsonRpcResponse<LoginData>> { response ->
            // on-request-done callback \/
            hideLoginLoad()

            val loginData: LoginData? = processLoginResponse(response)
            if (loginData != null) { // if the [processLoginResponse] method found valid LoginData -> save results and proceed to [DashboardActivity]
                loggedInPerson = loginData.person
                language = loginData.language?.name ?: DEFAULT_LANGUAGE
                jwt = loginData.jwt
                xsrf = loginData.xsrf

                startActivity(DashboardActivity::class.java)
                finish() // finish activity to remove it from the Task and disallow back-navigation to it once logged out (unless via startActivity())
            }

        }

        //todo - this is *bad*, but the first call to UT5_SERVICE.login(getLoginRequest()) takes OVER 1.2 SECONDS! ffs....
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                task.execute(if (isSilentLogin) UT5_SERVICE.silentLogin(getLoginRequest(true)) else UT5_SERVICE.login(getLoginRequest(false)))
                return null
            }
        }.execute()
    }
}
