package com.softwaregroup.underthekotlintree.model

data class Address(
        val addressId: Int,
        val actorId: Long?,
        val value: String,
        val frontEndRecordId: String?,
        val addressTypeId: String,
        val statusId: String,
        val oldValues: Address?,
        val city: String?,
        val lat: String?,
        val lng: String?,

        //todo - check if correct types
        val addressZone1: Long?,
        val addressZone2: Long?,
        val addressZone3: Long?,
        val addressZone4: Long?,

        val addressZone1Id: Long?,
        val addressZone2Id: Long?,
        val addressZone3Id: Long?,
        val addressZone4Id: Long?,

        val updatedBy: String?,
        val updatedOn: String?,
        val addressUnapprovedId: String?
)

