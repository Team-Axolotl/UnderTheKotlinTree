package com.softwaregroup.underthekotlintree.model

import android.support.annotation.DrawableRes
import com.fasterxml.jackson.annotation.JsonProperty
import com.softwaregroup.underthekotlintree.R

data class User(
        val actorId: Int,
        val userName: String,
        val firstName: String,
        val lastName: String,
        val roles: String,
        val branches: String,
        val isEnabled: Boolean,
        val isApproved: Boolean,
        val statusId: String,
        val rejectReason: String?,
        val failed: String?
)

data class UserFetchData(
        val user: ArrayList<User>
//todo - paging data
)


object UserStatusMap {
    val statusIdIconRes = mapOf<String, @DrawableRes Int>(
            "approved" to R.drawable.ic_approved,
            "pending" to R.drawable.ic_under_evaluation,
            "blocked" to R.drawable.ic_denied
    )
}

data class UserGetData(
        val person: Person,
        val phone: Array<PhoneItem>,
        val address: Array<Address>,
        val email: Array<String>,
        val emailUnapproved: Array<String>,
        val memberOF: Array<MemberOFItem>,
        val user: Array<UserItem>,
        @JsonProperty("policy.basic") val policyBasic: Array<PolicyBasicItem>,
        @JsonProperty("policy.credentials") val policyCredentials: Array<PolicyCredentialsItem>,
        @JsonProperty("user.hash") val userHash: Array<UserHashItem>,
        val rolesPossibleForAssign: Array<RolesPossibleForAssignItem>,
        val externalSystemCredentials: Array<ExternalSystemCredentialsItem>
)
