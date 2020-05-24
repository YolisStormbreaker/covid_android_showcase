package com.yolisstorm.library.google.maps_places.models

import android.os.Parcelable
import com.google.android.libraries.places.api.model.AddressComponents
import com.google.gson.annotations.SerializedName
import com.yolisstorm.library.google.maps_places.extensions.getAddressText
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressText (
	@SerializedName("country")
	val country : String? = null,
	@SerializedName("city")
	val city : String? = null,
	@SerializedName("street")
	val street : String? = null,
	@SerializedName("house")
	val house : String? = null,
	@SerializedName("flat")
	val flat : String? = null,
	@SerializedName("zipCode")
	val zipCode : String? = null
) : Parcelable {
	fun getBody() =
		"${city ?: ""}, ${street ?: ""}, ${house ?: ""}"
	
	private constructor(address: AddressText) : this(
		country = address.country,
		city = address.city,
		street = address.street,
		house = address.house,
		flat = address.flat,
		zipCode = address.zipCode
	)
	
	constructor(addressComponent: AddressComponents) : this(
		addressComponent.getAddressText()
	)
	
}