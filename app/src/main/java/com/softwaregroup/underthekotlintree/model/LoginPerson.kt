package com.softwaregroup.underthekotlintree.model

/**
 * Logged-in user's data and related data-classes.
 *
 * Models are mostly defined by the JSONs of data sent from the backend,
 * because they need to match during deserialization.
 */

data class LoginPerson(
        val actorId: Long,
        val firstName: String?,
        val lastName: String?
)

data class LoginData(
        val person: LoginPerson,
        val language: Language?,
        val jwt: Jwt,
        val xsrf: Xsrf
)

data class Jwt(val value: String)
data class Xsrf(val uuId: String)

data class Language(
        val languageId: Int,
        val iso2Code: String,
        val name: String,
        val locale: String
)
