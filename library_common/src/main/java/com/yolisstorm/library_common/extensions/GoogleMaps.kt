package com.yolisstorm.library_common.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.location.Location
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.collections.MarkerManager
import timber.log.Timber

fun GoogleMap.prepareMapStyle(context: Context?, @RawRes styleId: Int) {
	try {
		if (this.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, styleId))) {
			Timber.d("Styles successfully loaded.")
		} else
			Timber.d("Style parsing failed.")
	} catch (ex: Resources.NotFoundException) {
		Timber.d("Can't find style. Error = $ex")
	}
}

fun GoogleMap.preparePolygon(
	context: Context?,
	coordinates: List<LatLng>,
	@ColorRes polygonColorStroke: Int? = null,
	@ColorRes polygonColorFill: Int? = null
): Polygon? {
	context?.let {
		return addPolygon(
			with (PolygonOptions()) {
				if (polygonColorStroke != null)
					strokeColor(context.resources.getColor(polygonColorStroke))
				if (polygonColorFill != null)
					fillColor(context.resources.getColor(polygonColorFill))
				strokeWidth(6f)
				addAll(coordinates)
			}

		)
	}
	return null
}

fun GoogleMap.moveCamera(latLng: LatLng, zoom: Float = 18f) {
	animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
}

fun GoogleMap.moveCamera(location: Location, zoom: Float = 18f) {
	animateCamera(
		CameraUpdateFactory.newLatLngZoom(
			LatLng(location.latitude, location.longitude),
			zoom
		)
	)
}