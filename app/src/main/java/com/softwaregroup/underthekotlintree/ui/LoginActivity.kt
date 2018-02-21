package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.softwaregroup.underthekotlintree.JacksonObjMapper
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.storage.*
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // todo - move url definition to settings screen
        baseUrl = HttpUrl.Builder().host("192.168.128.65").port(8003).scheme("http").build()

        login_button.setOnClickListener {
            login_progress.visibility = VISIBLE
            LoginTask{ response ->
                login_progress.visibility = GONE

                if (response.isSuccessful && response.body()!!.error == null){
                    val loginData: LoginData = response.body()!!.result!!

                    loggedInPerson = loginData.person
                    language = loginData.language?.name?: DEFAULT_LANGUAGE
                    jwt = loginData.jwt
                    xsrf = loginData.xsrf
                }else{
                    showErrorMessage(response.body()?.error?.message)
                }
            }.execute(login_name.text.toList().toCharArray(), login_password.text.toList().toCharArray())
        }
    }

    private fun showErrorMessage(error: String?) {
        // todo - show proper visual clues and pictures and stuff
        toast(error?: "null")
    }
}


class LoginTask(private val resultOpp: (Response<JsonRpcResponse<LoginData>>) -> Unit) : AsyncTask<CharArray, Unit, Response<JsonRpcResponse<LoginData>>>(){

    override fun doInBackground(vararg params: CharArray?): Response<JsonRpcResponse<LoginData>>{
        if (params.size != 2) throw IllegalArgumentException("The LoginTask *MUST* be executed with 2 String params - [0] = u.name, [1] = u.pass!")

        val ut5Service = Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build())
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(JacksonObjMapper))
                .build()
                .create(Ut5Service::class.java)

        val request = JsonRpcRequest(
                method = REQUEST_IDENTITY_CHECK,
                params = mapOf(
                        "username" to params[0]!!.joinToString(separator = "").trim(), //todo - to trim or not to trim? That is ... *a*... question.
                        "password" to params[1]!!.joinToString(separator = "").trim(), //todo - to trim or not to trim? That is ... *a*... question.
                        "timezone" to TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT),
                        "channel" to "mobile"
                )
        )

        // todo - make cancelable

        return ut5Service.login(request).execute()
    }

    override fun onPostExecute(result: Response<JsonRpcResponse<LoginData>>) {
        resultOpp(result)
    }
}