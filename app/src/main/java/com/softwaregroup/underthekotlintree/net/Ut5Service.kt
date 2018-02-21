package com.softwaregroup.underthekotlintree.net


import com.softwaregroup.underthekotlintree.model.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// REQUEST METHODS
const val REQUEST_IDENTITY_CHECK = "identity.check"
// END REQUEST METHODS

interface Ut5Service {

    @POST("/login")
    fun login(@Body body: JsonRpcRequest): Call<JsonRpcResponse<Login>>

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


data class JsonRpcRequest(
        val id: Int = 1,
        val jsonrpc: String = "2.0",
        val method: String,
        val params: Map<String, Any>
)