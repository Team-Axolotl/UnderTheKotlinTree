package com.softwaregroup.underthekotlintree.net

import android.util.Log
import com.softwaregroup.underthekotlintree.model.Login
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Ut5Service {


    @POST("/login")
    fun login(@Body body: RequestBody): Call<JsonRpcResponse<Login>>

}

data class JsonRpcResponse<T>(
        val id: Int,
        val jsonrpc: String,
        val result: T?,
        val error: JsonRpcError?
)

data class JsonRpcError(
        val code: String,
        val message: String,
        val errorPrint: String?,
        val type: String
)

class JsonRpcRequest (val id: Int = 1, val method: String, val params: MutableMap<String, String>){

    override fun toString(): String {
        val result = """
            "id":"$id",
            "jsonrpc":"2.0",
            "method":"$method",
            "params":{
                $params
            }"
            """

        Log.wtf("TAAAG" , result)
        return result
    }

}