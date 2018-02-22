package com.softwaregroup.underthekotlintree

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/** Configured objectMapper for JSON serialization/deserialization */
object JacksonObjMapper : ObjectMapper() {
    init {
        registerModule(KotlinModule())
        enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}
