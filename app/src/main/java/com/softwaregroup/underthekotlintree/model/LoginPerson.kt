package com.softwaregroup.underthekotlintree.model

import java.io.Serializable

data class LoginPerson(
        val actorId: Long?,
        val firstName: String?,
        val lastName: String?
) : Serializable

data class Login(val person: LoginPerson)
