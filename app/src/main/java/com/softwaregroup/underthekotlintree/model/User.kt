package com.softwaregroup.underthekotlintree.model

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
        val rejectReason: String?
//        val failed: null
)

data class UserFetchData(
        val user: ArrayList<User>
//todo - paging data
)