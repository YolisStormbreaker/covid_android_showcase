package com.yolisstorm.repository.network.covid_stats.enums

import com.google.gson.annotations.SerializedName

enum class BadRequestOutputStates {
	
	Unknown,
	
	/**
	 * Wrong firebase token
	 */
	@SerializedName("firebase_auth_required")
	FirebaseAuthRequire,
	
	/**
	 * User missing in database
	 */
	@SerializedName("registration_required")
	RegistrationRequired,
	
	/**
	 * The specified 'grant_type' parameter is not supported.
	 */
	@SerializedName("unsupported_grant_type")
	UnsupportedGrantType,
	
	/**
	 * This client application is not allowed to use the specified grant type.
	 */
	@SerializedName("unauthorized_client")
	UnauthorizedClient
	
}
