package com.softwaregroup.underthekotlintree.model

data class LoginPerson(
        val actorId: Long,
        val firstName: String?,
        val lastName: String?
)

data class Login(
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
