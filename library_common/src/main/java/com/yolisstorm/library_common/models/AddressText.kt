package com.yolisstorm.library_common.models

import android.location.Address
import android.os.Parcelable
import com.google.android.libraries.places.api.model.AddressComponent
import com.google.android.libraries.places.api.model.AddressComponents
import com.google.gson.annotations.SerializedName
import com.yolisstorm.library_common.extensions.getAddressText
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
	val zipCode : String? = null,
	@SerializedName("additional")
	val additional : String? = null
) : Parcelable {
	fun getBody() =
		"${city ?: ""}, ${street ?: ""}, ${house ?: ""}"
	
	private constructor(address: AddressText) : this(
		country = address.country,
		city = address.city,
		street = address.street,
		house = address.house,
		flat = address.flat,
		zipCode = address.zipCode,
		additional = address.additional
	)
	
	constructor(addressComponent: AddressComponents) : this(
		addressComponent.getAddressText()
	)
	
}