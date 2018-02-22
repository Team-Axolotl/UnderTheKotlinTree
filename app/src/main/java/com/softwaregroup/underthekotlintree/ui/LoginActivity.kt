package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.storage.*
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import java.util.*

/**
 * First Activity that the User sees when opening the app.
 * Handles logging in via authentication with credentials to a server as set in [SettingsActivity]
 */
class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // todo - move url definition to settings screen
        baseUrl = HttpUrl.Builder().scheme("http").host("192.168.128.65").port(8004).build()


        loginButton.setOnClickListener {
            beginLoginLoad()

            // Create an AsyncTask for executing login request.
            HttpAsyncTask<JsonRpcResponse<LoginData>> { response ->
                // on-request-done callback \/
                endLoginLoad()

                val loginData: LoginData? = processLoginResponse(response)
                if (loginData != null) { // if the [processLoginResponse] method found valid LoginData -> save results and proceed to [DashboardActivity]
                    loggedInPerson = loginData.person
                    language = loginData.language?.name ?: DEFAULT_LANGUAGE
                    jwt = loginData.jwt
                    xsrf = loginData.xsrf

                    startActivity(Intent(this, DashboardActivity::class.java))
                }

            }.execute(UT5_SERVICE.login(getLoginRequest()))
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

    private fun beginLoginLoad() {
        loginProgress.visibility = VISIBLE
        loginButton.isEnabled = false
    }

    private fun endLoginLoad() {
        loginProgress.visibility = GONE
        loginButton.isEnabled = true
    }

    /** Generate a [JsonRpcRequest] for the [Ut5Service.login] */
    private fun getLoginRequest(): JsonRpcRequest {
        return JsonRpcRequest(
                method = REQUEST_IDENTITY_CHECK,
                params = mapOf(
                        "username" to loginName.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                        "password" to loginPassword.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                        "timezone" to TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT),
                        "channel" to "mobile"
                )
        )
    }

    /** Show error ui elements */
    private fun showErrorMessage(message: String) {
        // todo - impl proper
        toast(message)
    }

}