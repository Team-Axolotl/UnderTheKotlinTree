package com.softwaregroup.underthekotlintree

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object JacksonObjMapper: ObjectMapper() {
    init{
        registerModule(KotlinModule())
        enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

//    fun createRpcJson(id: Int = 1, jsonrpc: String = "2.0", method: String, params: Map<String, String>): String{
//
//        val paramsNode = createObjectNode() // create a node for the params
//        for ((key, value) in params) paramsNode.put(key, value) // populate it from the passed map
//
//        val rootObjectNode = createObjectNode()
//        rootObjectNode.put("id", id)
//                .put("jsonrpc", jsonrpc)
//                .put("method", method)
//                .set("params", paramsNode)
//
//    }

}
