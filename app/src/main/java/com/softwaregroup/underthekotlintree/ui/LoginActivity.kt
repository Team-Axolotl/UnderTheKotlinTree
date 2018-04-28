package com.softwaregroup.underthekotlintree.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.util.toast
import softwaregroup.com.ease_ui.linearLayout

/**
 * First Activity that the User sees when opening the app.
 * Handles logging in via authentication with credentials to a server as set in [SettingsActivity]
 */
class LoginActivity : BaseActivity(), OnClickListener {

    override fun onClick(v: View?) {
//        when (v) {
//            loginButton -> if (validateLoginInput()) beginLogin()
//            loginSettingsButton -> startActivity(SettingsActivity::class.java)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //todo - the commented block is TEMPORARY. For EaseUI testing purposes!
        setContentView(
                linearLayout(this) {
                    textView {
                        text = "testText1"
                    }
                    textView {
                        text = "testText2"
                        textSizeSp = 26F
                        widthDp = 100
                        heightDp = 200
                    }
                    textView {
                        text = "testText3"
                        heightDp = 64
                    }
                    textView {
                        text = "testText4"
                        widthDp = 10
                    }
                    textView {
                        hint = "testHint5"
                    }
                    textView {
                        hint = "testHint6"
                        widthDp = ViewGroup.LayoutParams.MATCH_PARENT
                    }
                    button {
                        text = "?"
                        onClickListener = OnClickListener {
                            toast("WORKEDED")
                        }
                    }
                }
        )
    }
}

//        loginButton.setOnClickListener(this)
//        loginSettingsButton.setOnClickListener(this)
//
//        if (jwt != null && xsrf != null) {
//            beginLogin(isSilentLogin = true)
//            Log.wtf("TAAAG", "${jwt!!.value} : ${xsrf!!.uuId}")
//        }
//    }
//
//    private fun showLoginLoad() {
//        loginProgress.visibility = VISIBLE
//        loginButton.isEnabled = false
//        loginNameInput.isEnabled = false
//        loginPasswordInput.isEnabled = false
//    }
//
//    private fun hideLoginLoad() {
//        loginProgress.visibility = INVISIBLE
//        loginButton.isEnabled = true
//        loginNameInput.isEnabled = true
//        loginPasswordInput.isEnabled = true
//    }
//
//    private fun validateLoginInput(): Boolean {
//        return when {
//            loginNameInput.text.isBlank() -> {
//                loginNameInput.error = getString(R.string.error_invalid_input)
//                loginNameInput.requestFocus()
//                false
//            }
//
//            loginPasswordInput.text.isBlank() -> {
//                loginPasswordInput.error = getString(R.string.error_invalid_input)
//                loginPasswordInput.requestFocus()
//                false
//            }
//
//            else -> true
//        }
//    }
//
//
//    private fun beginLogin(isSilentLogin: Boolean = false) {
//        showLoginLoad()
//
//        // Create an AsyncTask for executing login request.
//        val task = HttpAsyncTask<LoginData> { response ->
//            hideLoginLoad()
//
//            if (response.isSuccess) {
//                val loginData: LoginData = response.result!!
//                loggedInPerson = loginData.person
//                language = loginData.language?.name ?: DEFAULT_LANGUAGE
//                jwt = loginData.jwt
//                xsrf = loginData.xsrf
//
//                startActivity(DashboardActivity::class.java)
//                finish() // finish activity to remove it from the Task and disallow back-navigation to it once logged out (unless via startActivity())
//            } else {
//                showHttpErrorMessage(response)
//                clearMemStorage() // clear jwt and xsrf if login failed. Will be re-stored on successful u.name+pass login
//            }
//        }
//
//        if (isSilentLogin)
//            task.execute(UT5_SERVICE.silentLogin(getLoginRequest(true)))
//        else
//            task.execute(UT5_SERVICE.login(getLoginRequest(false)))
//    }
//
//    /** Generate a [JsonRpcRequest] for the [Ut5Service.login] */
//    private fun getLoginRequest(isForSilentLogin: Boolean = false): JsonRpcRequest {
//        return when (isForSilentLogin) {
//            false -> JsonRpcRequest(
//                    method = REQUEST_IDENTITY_CHECK,
//                    params = mapOf(
//                            "username" to loginNameInput.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
//                            "password" to loginPasswordInput.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
//                            "timezone" to TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT),
//                            "channel" to "mobile"
//                    ))
//            true -> JsonRpcRequest(method = REQUEST_IDENTITY_CHECK, params = mapOf("channel" to "mobile"))
//        }
//    }
//}
