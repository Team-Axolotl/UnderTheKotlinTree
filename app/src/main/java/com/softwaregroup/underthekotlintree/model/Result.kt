package com.softwaregroup.underthekotlintree.model

//todo - what DAFUQ is this shit?! :@
data class Result(
	val userActorDeviceUnapproved: List<Any?>? = null,
	val emailUnapproved: List<Any?>? = null,
	val externalSystemCredentialsUnapproved: List<Any?>? = null,
	val address: List<Address?>? = null,
	val rolesUnapproved: List<Any?>? = null,
	val document: List<Any?>? = null,
	val userActorDevice: List<Any?>? = null,
	val userUnapproved: List<Any?>? = null,
	val userHashUnapproved: List<Any?>? = null,
	val attachment: List<Any?>? = null,
	val phone: List<PhoneItem?>? = null,
	val policyBasic: List<PolicyBasicItem?>? = null,
	val userHash: List<UserHashItem?>? = null,
	val person: Person? = null,
	val externalSystemCredentials: List<ExternalSystemCredentialsItem?>? = null,
	val memberOfUnapproved: List<Any?>? = null,
	val personUnapproved: Any? = null,
	val phoneUnapproved: List<Any?>? = null,
	val policyCredentials: List<PolicyCredentialsItem?>? = null,
	val memberOF: List<MemberOFItem?>? = null,
	val rolesPossibleForAssign: List<RolesPossibleForAssignItem?>? = null,
	val user: List<User?>? = null,
	val email: List<Any?>? = null,
	val addressUnapproved: List<Any?>? = null
)
