package com.yolisstorm.library.google.maps_places.utils

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.maps.android.PolyUtil
import com.yolisstorm.library.google.maps_places.models.PlaceLocationPolygon
import com.yolisstorm.library.extensions.second
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.*


suspend fun getMinimumBoundsBox(polygonPoints: List<LatLng>): RectangularBounds? {
	if (polygonPoints.first() != polygonPoints.last()) return null
	return withContext(Dispatchers.Default) {
		val minimumLat = polygonPoints.minBy { it.latitude }?.latitude ?: 0.0
		val minimumLng = polygonPoints.minBy { it.longitude }?.longitude ?: 0.0
		val maximumLat = polygonPoints.maxBy { it.latitude }?.latitude ?: 0.0
		val maximumLng = polygonPoints.maxBy { it.longitude }?.longitude ?: 0.0
		val rightTop = LatLng(maximumLat, maximumLng)
		val leftBottom = LatLng(minimumLat, minimumLng)
		Timber.d("getMinimumBoundsBox() - rightTop - \n${rightTop.longitude},\n${rightTop.latitude}\n | leftBottom - \n${leftBottom.longitude},\n${leftBottom.latitude}\n")
		RectangularBounds.newInstance(
			LatLngBounds(
				leftBottom,
				rightTop
			)
		)
	}
}

suspend fun findCentreOfPolygon(polygonPoints: List<LatLng>): LatLng? {
	if (polygonPoints.isEmpty()) return null
	if (polygonPoints.first() != polygonPoints.last()) return null
	return withContext(Dispatchers.Default) {
		val result = arrayOf(0.0, 0.0)
		polygonPoints.indices.forEach {
			result[0] += polygonPoints[it].latitude
			result[1] += polygonPoints[it].longitude
		}
		val totalPoints = polygonPoints.size
		result[0] = result[0] / totalPoints
		result[1] = result[1] / totalPoints
		Timber.d("findCentreOfPolygon() - center - \n${result[1]},\n${result[0]}\n")
		LatLng(result[0], result[1])
	}
}

suspend fun calculateMidpointBetweenPoints(listOfPoints: List<LatLng>): LatLng =
	if (listOfPoints.size > 2) {
		val list = mutableListOf<LatLng>()
		list.addAll(listOfPoints)
		list.add(listOfPoints.first())
		findCentreOfPolygon(list)
			?: LatLng(57.856402, 33.528651)
	} else {
		calculateMidpointBetweenTwoPoints(
			listOfPoints.first(),
			listOfPoints.second()
		)
	}

@Suppress("NonAsciiCharacters")
suspend fun calculateMidpointBetweenTwoPoints(
	firstPointLatLng: LatLng,
	secondPointLatLng: LatLng
): LatLng {
	return withContext(Dispatchers.Default) {
		//φ - latitude
		//λ - longitude
		val φ1 = Math.toRadians(firstPointLatLng.latitude)
		val λ1 = Math.toRadians(firstPointLatLng.longitude)
		val φ2 = Math.toRadians(secondPointLatLng.latitude)
		val λ2 = Math.toRadians(secondPointLatLng.longitude)
		val Bx = cos(φ2) * cos(λ2 - λ1)
		val By = cos(φ2) * sin(λ2 - λ1)

		val φm =
			atan2(
				y = sin(φ1) + sin(φ2),
				x = sqrt(
					(cos(φ1) + Bx).pow(2) + By.pow(2)
				)
			)
		val λm = λ1 + atan2(By, cos(φ1) + Bx)
		Timber.d(
			"calculateMidpointBetweenTwoPoints() - center - \n${Math.toDegrees(λm)},\n${Math.toDegrees(φm)}\n"
		)
		LatLng(Math.toDegrees(φm), Math.toDegrees(λm))
	}
}

suspend fun calculateCameraUpdateForMapCenterOnPolygonOfMarkers(pointsList: List<LatLng?>, padding: Int = 180): CameraUpdate? {
	if (pointsList.filterNotNull().isEmpty())
		return null
	return withContext(Dispatchers.Default) {
		CameraUpdateFactory.newLatLngBounds(
			with(LatLngBounds.builder()) {
				pointsList.filterNotNull().forEach {
					this.include(it)
				}
				this.build()
			},
			padding
		)
	}
}

fun rectangleBiasNewInstanceOrNull(northern: LatLng, southern: LatLng) =
	try {
		if (southern.latitude > northern.latitude && southern.longitude > northern.longitude)
			RectangularBounds.newInstance(northern, southern)
		else
			RectangularBounds.newInstance(southern, northern)
	} catch (ex: java.lang.Exception) {
		Timber.e("RectangularBounds.newInstanceOrNull() - ${ex.localizedMessage}")
		null
	}

fun checkIfPolygonsContainsPoint(
	point: LatLng,
	polygons: List<PlaceLocationPolygon>
): Boolean {
	polygons.forEach {
		if (isPolygonContainPoint(
				point,
				it
			)
		)
			return true
	}
	return false
}

fun isPolygonContainPoint(point: LatLng, polygon: PlaceLocationPolygon) =
	PolyUtil.containsLocation(point, polygon.getPoints(), true)
