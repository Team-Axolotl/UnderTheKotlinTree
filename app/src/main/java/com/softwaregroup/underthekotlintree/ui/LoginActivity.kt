package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.softwaregroup.underthekotlintree.JacksonObjMapper
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.storage.*
import com.softwaregroup.underthekotlintree.util.DashboardActivity
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*

import java.util.concurrent.TimeUnit

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // todo - move url definition to settings screen
        baseUrl = HttpUrl.Builder().scheme("http").host("192.168.128.65").port(8004).build()

        val client = OkHttpClient.Builder()
                .connectTimeout(30_000, TimeUnit.MILLISECONDS)
                .readTimeout(30_000, TimeUnit.MILLISECONDS)
                .addInterceptor(AuthInterceptor())
                .build()

        val ut5Service = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(JacksonObjMapper))
                .build()
                .create(Ut5Service::class.java)


        login_button.setOnClickListener {
            login_progress.visibility = VISIBLE
            login_button.isEnabled = false

            val request = JsonRpcRequest(
                    method = REQUEST_IDENTITY_CHECK,
                    params = mapOf(
                            "username" to login_name.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                            "password" to login_password.text.toString().trim(), //todo - to trim or not to trim? That is ... *a* question.
                            "timezone" to TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT),
                            "channel" to "mobile"
                    )
            )

            HttpAsyncTask<JsonRpcResponse<LoginData>> { response ->
                login_progress.visibility = GONE
                login_button.isEnabled = true

                if (response.isSuccess && response.result!!.error == null) {
                    val jsonRpcResponse = response.result
                    val loginData: LoginData = jsonRpcResponse.result!!

                    loggedInPerson = loginData.person
                    language = loginData.language?.name ?: DEFAULT_LANGUAGE
                    jwt = loginData.jwt
                    xsrf = loginData.xsrf

                    startActivity(Intent(this, DashboardActivity::class.java))
                } else {
                    showErrorMessage(when (response.isSuccess) {
                        true -> response.result!!.error!!.message
                        false -> {
                            response.errorCode!!
                            getString(response.errorCode.messageStringId)
                        }
                    })
                }
            }.execute(ut5Service.login(request))
        }

    }

    private fun showErrorMessage(message: String) {
        // todo - impl proper
        toast(message)
    }

}