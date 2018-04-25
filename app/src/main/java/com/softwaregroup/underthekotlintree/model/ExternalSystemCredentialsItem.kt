package com.softwaregroup.underthekotlintree.model

data class ExternalSystemCredentialsItem(
        val genericUdf: GenericUdf? = null,
        val organizationName: String? = null,
        val nonGenericUdf: NonGenericUdf? = null,
        val isGeneric: Boolean? = null,
        val userToExternalUserId: String? = null,
        val hasPassword: Int? = null,
        val userName: String? = null,
        val isActive: Boolean? = null,
        val organizationId: String? = null,
        val userToExternalUserUnapprovedId: Any? = null,
        val externalUserId: Int? = null,
        val externalSystemName: String? = null,
        val userAlias: String? = null,
        val externalSystemId: Int? = null
)
