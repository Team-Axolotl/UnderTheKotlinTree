package com.softwaregroup.underthekotlintree.model

data class UserHashItem(
	val hashUnapprovedId: Any? = null,
	val identifier: String? = null,
	val failedAttempts: Int? = null,
	val lastAttempt: String? = null,
	val lastChange: String? = null,
	val expireDate: String? = null,
	val type: String? = null,
	val hashId: String? = null
)
