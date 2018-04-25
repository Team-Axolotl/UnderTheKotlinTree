package com.softwaregroup.underthekotlintree.net

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/** Configured objectMapper for JSON serialization/deserialization */
object JacksonObjMapper : ObjectMapper() {
    init {
        registerModule(KotlinModule())
        enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}

/** Wrap a [JacksonConverterFactory] in order to send the reposnse for parsing as a [JsonRpcResponse] wrapper, instead of <T> */
class WrappedJacksonConverterFactory(private val converterFactory: JacksonConverterFactory) : Converter.Factory() {

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        return converterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        return converterFactory.stringConverter(type, annotations, retrofit)
    }

    /** Get a usable instance of a retrofit2 [Converter] tailored for our custom response wrapper*/
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        // Generate the popper Type for JsonRpcRequest<T>
        val rpcWrapperType: ParameterizedType = object : ParameterizedType {
            override fun getRawType() = JsonRpcResponse::class.java
            override fun getOwnerType() = null
            override fun getActualTypeArguments() = arrayOf(type)
        }

        // Return a Converter wrapper for the JsonRpcResponse wrapper (wrap ALL the things ヘ( ^o^)ノ )
        val jacksonConverter = converterFactory.responseBodyConverter(rpcWrapperType, annotations, retrofit) as Converter<ResponseBody, JsonRpcResponse<*>>
        return ResponseBodyConverter(jacksonConverter)
    }
}

/** Wrapper over the retrofit2 [Converter] specificly for Jackson & [JsonRpcResponse] items */
class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, JsonRpcResponse<T>>) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        // Before the call.execute() returns (in the HttpAsyncTask [HttpAsyncTask.kt:25])
        // it passes through the deserialization logic.
        // And here we can throw a custom exception to pass an error on (if necessary),
        // alternatively discard the rpc part of teh response and return the 'meat of it'
        val rpcResponse:JsonRpcResponse<T> = converter.convert(value)

        if(rpcResponse.error != null)
            throw RpcException(rpcResponse.error)
        else
            return rpcResponse.result
    }
}

class RpcException(val rpcError: JsonRpcError) : RuntimeException(rpcError.message)

