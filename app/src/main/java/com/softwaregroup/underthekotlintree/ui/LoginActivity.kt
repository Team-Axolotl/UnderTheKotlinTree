package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.softwaregroup.underthekotlintree.JacksonObjMapper
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.Login
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.storage.baseUrl
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class LoginActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // todo - move url definition to settings screen
        baseUrl = HttpUrl.Builder().host("192.168.128.65").port(8003).scheme("http").build()
        val ut5Service = Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build())
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(JacksonObjMapper))
                .build().create<Ut5Service>(Ut5Service::class.java)


        login_button.setOnClickListener {
            val requestBody = JsonRpcRequest(
                    method = REQUEST_IDENTITY_CHECK,
                    params = mapOf("password" to login_password.text.toString(), "username" to login_name.text.toString(), "timezone" to "+02:00", "channel" to "web")
            )

            ut5Service.login(requestBody).enqueue(object : Callback<JsonRpcResponse<Login>> {
                override fun onFailure(call: Call<JsonRpcResponse<Login>>?, t: Throwable?) {
                    Log.wtf("TAAAG_err", t?.message, t)
                }

                override fun onResponse(call: Call<JsonRpcResponse<Login>>?, response: Response<JsonRpcResponse<Login>>?) {
                    login_result.text = " \t ${response?.body()?.result?.language} \n\n\t ${response?.body()?.result?.jwt} \n\n\t ${response?.body()?.result?.xsrf} \n\n\t ${response?.body()?.result?.person}"
                    Log.wtf("TAAAG", response?.body()?.result?.toString())
                }
            })
        }
    }
}