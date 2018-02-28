package com.softwaregroup.underthekotlintree.model

import okhttp3.Connection
import okhttp3.Request
import okhttp3.Response

data class UserItem(
	val actorId: String?,
	val updatedBy: Any?,
	val rejectReason: Any?,
	val numberFormat: Any?,
	val isDeleted: Boolean?,
	val statusId: String?,
	val policyId: String?,
	val dateFormat: Any?,
	val isEnabled: Boolean?,
	val isNew: Int?,
	val primaryLanguageId: String?,
	val isApproved: Boolean?
)


interface Interceptor {
	fun intercept(chain: Chain): Response

	interface Chain {
		fun request(): Request



		fun proceed(request: Request): Response

		/**
		 * Returns the connection the request will be executed on. This is only available in the chains
		 * of network interceptors; for application interceptors this is always null.
		 */
		fun connection(): Connection?
	}
}