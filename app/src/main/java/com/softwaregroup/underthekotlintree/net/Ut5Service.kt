package com.softwaregroup.underthekotlintree.net


import com.softwaregroup.underthekotlintree.model.LoginData
import com.softwaregroup.underthekotlintree.model.UserFetchData
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.storage.baseUrl
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// REQUEST METHODS
const val REQUEST_IDENTITY_CHECK = "identity.check"
const val REQUEST_USER_USER_FETCH = "user.user.fetch"
const val REQUEST_USER_USER_GET = "user.user.get"
// END REQUEST METHODS

interface Ut5Service {

    @POST("/login")
    fun login(@Body body: JsonRpcRequest): Call<LoginData>

    @POST("/rpc")
    fun silentLogin(@Body body: JsonRpcRequest): Call<LoginData>

    @POST("/rpc")
    fun userFetch(@Body body: JsonRpcRequest): Call<UserFetchData>

    @POST("/rpc")
    fun userGet(@Body body: JsonRpcRequest): Call<UserGetData>

}

var UT5_SERVICE: Ut5Service = createUt5Service()

fun createUt5Service(): Ut5Service = Retrofit.Builder()
        .client(UT5_CLIENT)
        .baseUrl(baseUrl)
        .addConverterFactory(WrappedJacksonConverterFactory(JacksonConverterFactory.create(JacksonObjMapper)))
        .build()
        .create(Ut5Service::class.java)

data class JsonRpcResponse<out T>(
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