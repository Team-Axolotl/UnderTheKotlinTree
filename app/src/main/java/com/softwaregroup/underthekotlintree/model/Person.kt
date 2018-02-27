package com.softwaregroup.underthekotlintree.model

data class Person(
	val actorId: Int,
	val frontEndRecordId: String?,
	val lastName: String,
	val firstName: String,
    val nationalId: String?,
    val dateOfBirth: String?,
    val placeOfBirth: String?,
    val nationality: String?,
    val gender: String?,
    val bioId: Long?,
	val oldValues: PersonOldValues?,
    val udf: PersonUdf?,
    val phoneModel: String?,
    val computerModel: String?,
    val maritalStatusId: Int?,
    val age: Int?
)


class PersonOldValues(

)

class PersonUdf(

)