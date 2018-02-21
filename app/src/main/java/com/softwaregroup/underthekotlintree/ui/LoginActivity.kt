package com.softwaregroup.underthekotlintree.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.softwaregroup.underthekotlintree.JacksonObjMapper
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.Login
import com.softwaregroup.underthekotlintree.net.JsonRpcResponse
import com.softwaregroup.underthekotlintree.net.Ut5Service
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class LoginActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val ut5Service = Retrofit.Builder()
                .baseUrl("http://192.168.128.65:8004")
                .addConverterFactory(JacksonConverterFactory.create(JacksonObjMapper))
                .build().create<Ut5Service>(Ut5Service::class.java)


        login_button.setOnClickListener {
            val requestBody = RequestBody.create(
                    MediaType.parse("application/json"),
                    """{"id":2, "jsonrpc":"2.0", "method":"identity.check", "params":{"password":"${login_password.text}","username":"${login_name.text}","timezone":"+02:00","channel":"web"}}"""
            )

            ut5Service.login(requestBody).enqueue(object : Callback<JsonRpcResponse<Login>> {
                override fun onFailure(call: Call<JsonRpcResponse<Login>>?, t: Throwable?) {
                    Log.wtf("TAAAG", t?.message, t)
                }

                override fun onResponse(call: Call<JsonRpcResponse<Login>>?, response: Response<JsonRpcResponse<Login>>?) {
                    login_result.text = response?.body()?.result?.person.toString()
                    Log.wtf("TAAAG", response?.body()?.result?.toString())
                }
            })
        }

    }

}