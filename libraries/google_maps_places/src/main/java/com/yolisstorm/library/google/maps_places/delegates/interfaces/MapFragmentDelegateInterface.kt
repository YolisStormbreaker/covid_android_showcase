package com.yolisstorm.library.google.maps_places.delegates.interfaces

import android.content.Context
import android.location.Location
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.yolisstorm.library.google.maps_places.models.PlaceLocationPoint
import com.yolisstorm.library.google.maps_places.models.PlaceLocationPolygon
import java.util.*

interface MapFragmentDelegateInterface : OnMapReadyCallback {

	suspend fun initMap(mapFragment: SupportMapFragment)

	val isPolygonsDrew: LiveData<Boolean>
	fun updateIsPolygonDrew(n: Boolean)

	fun drawPolygonsAndUpdateStatus(
		listOfDestinations: List<PlaceLocationPolygon>
	)

	fun drawPolygons(
		listOfDestinations: List<PlaceLocationPolygon>
	)

	suspend fun prepareMarkersOnMaps(
		activity: FragmentActivity?,
		context: Context?,
		markers: List<PlaceLocationPoint>
	)

	fun updateMapZoomAndCenterPoint(cameraUpdate: CameraUpdate)
	fun moveMapTo(center: LatLng)

	fun updateActionWhenMapInitFinished(action: (() -> Unit)?)
	fun updateActionCameraIdleStateListener(action: ((latLng: LatLng) -> Unit)?)
	fun updateOnMarkerClickListener(action: GoogleMap.OnMarkerClickListener?)
	fun updateOnClusterItemClickListener(action: ClusterManager.OnClusterItemClickListener<PlaceLocationPoint>?)
	fun getMap(): GoogleMap?
	fun resetMap()
	fun moveCamera(location: LatLng, zoom: Float = 9f)
	fun moveCamera(location: Location, zoom: Float = 9f)

	fun clearAllObjectsOnMap()

}