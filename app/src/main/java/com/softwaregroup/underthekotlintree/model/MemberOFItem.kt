package com.softwaregroup.underthekotlintree.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MemberOFItem(
	val isDefault: Boolean? = null,
	val organizationName: String? = null,
	@JsonProperty("object") val objectName: String? = null
)
