package com.yolisstorm.library.google.maps_places.delegates.impl

import android.content.Context
import android.location.Location
import androidx.annotation.RawRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.collections.PolygonManager
import com.yolisstorm.library.google.maps_places.delegates.interfaces.MapFragmentDelegateInterface
import com.yolisstorm.library.google.maps_places.extensions.moveCamera
import com.yolisstorm.library.google.maps_places.extensions.prepareMapStyle
import com.yolisstorm.library.google.maps_places.extensions.preparePolygon
import com.yolisstorm.library.google.maps_places.models.PlaceLocationPoint
import com.yolisstorm.library.google.maps_places.models.PlaceLocationPolygon
import timber.log.Timber

class MapFragmentDelegate(
	private val context: Context,
	@RawRes
	private val mapStyleResId: Int? = null
) : MapFragmentDelegateInterface {

	private lateinit var markerManager: MarkerManager
	private lateinit var polygonManager: PolygonManager
	private var clusterManagers: MutableList<ClusterManager<PlaceLocationPoint>> = mutableListOf()


	private var actionByMapInitFinished: (() -> Unit)? = null
	override fun updateActionWhenMapInitFinished(action: (() -> Unit)?) {
		actionByMapInitFinished = action
	}

	private val _isPolygonsDrew = MutableLiveData<Boolean>(false)
	override val isPolygonsDrew: LiveData<Boolean>
		get() = _isPolygonsDrew

	override fun updateIsPolygonDrew(n: Boolean) {
		_isPolygonsDrew.value = n
	}

	private var actionCameraIdleStateListener: ((LatLng) -> Unit)? = null
	override fun updateActionCameraIdleStateListener(action: ((latLng: LatLng) -> Unit)?) {
		actionCameraIdleStateListener = action
		map?.let {
			it.setOnCameraIdleListener {
				Timber.d("MapMoved - current mid LatLng = ${it.cameraPosition.target}")
				action?.invoke(it.cameraPosition.target)
			}
		}
	}

	private var onClusterItemClickListener: ClusterManager.OnClusterItemClickListener<PlaceLocationPoint>? =
		null

	override fun updateOnClusterItemClickListener(action: ClusterManager.OnClusterItemClickListener<PlaceLocationPoint>?) {
		onClusterItemClickListener = action
		clusterManagers.forEach { cluster ->
			cluster.setOnClusterItemClickListener {
				Timber.d("Cluster item taped - ${it.getLatLng()}")
				action?.onClusterItemClick(it) == true
			}
		}
	}

	private var onMarkerClickListener: GoogleMap.OnMarkerClickListener? = null
	override fun updateOnMarkerClickListener(action: GoogleMap.OnMarkerClickListener?) {
		onMarkerClickListener = action
		map?.let { googleMap ->
			googleMap.setOnMarkerClickListener {
				Timber.d("Marker taped - tag = ${it.tag} - title = ${it.title}")
				action?.onMarkerClick(it) == true
			}
		}
	}

	private var map: GoogleMap? = null

	override fun getMap(): GoogleMap? = map

	override fun resetMap() {
		map?.let {
			clearAllObjectsOnMap()
			map = null
		}
	}

	override suspend fun initMap(mapFragment: SupportMapFragment) {
		mapFragment.getMapAsync(this)
	}

	override fun moveCamera(location: LatLng, zoom: Float) {
		map?.moveCamera(location, zoom)
	}

	override fun moveCamera(location: Location, zoom: Float) {
		map?.moveCamera(location, zoom)
	}

	override fun onMapReady(map: GoogleMap?) {
		map?.let { googleMap ->
			Timber.d("onMapReady()")
			this.map = googleMap
			if (mapStyleResId != null) {
				Timber.d("onMapReady() - prepareMapStyle")
				googleMap.prepareMapStyle(context, mapStyleResId)
			}
			Timber.d("onMapReady() - isCompassEnabled - false")
			with(map.uiSettings) {
				isCompassEnabled = false
				isMyLocationButtonEnabled = false
				isIndoorLevelPickerEnabled = false
				isMapToolbarEnabled = false
				isTiltGesturesEnabled = false
			}
			markerManager = MarkerManager(googleMap)
			polygonManager = PolygonManager(googleMap)

			updateActionCameraIdleStateListener(actionCameraIdleStateListener)
			updateActionWhenMapInitFinished(actionByMapInitFinished)
			updateOnMarkerClickListener(onMarkerClickListener)
			updateOnClusterItemClickListener(onClusterItemClickListener)
			actionByMapInitFinished?.invoke()
		}
	}

	override fun drawPolygonsAndUpdateStatus(listOfDestinations: List<PlaceLocationPolygon>) {
		map?.let { googleMap ->
			if (isPolygonsDrew.value?.not() == true && listOfDestinations.isNotEmpty()) {
				listOfDestinations.forEach { polygon ->
					googleMap.preparePolygon(context, polygon.getPoints())
				}
				updateIsPolygonDrew(true)
			}
		}
	}

	override fun drawPolygons(listOfDestinations: List<PlaceLocationPolygon>) {
		map?.let { googleMap ->
			if (listOfDestinations.isNotEmpty()) {
				listOfDestinations.forEach { polygon ->
					googleMap.preparePolygon(context, polygon.getPoints())
				}
			}
		}
	}

	override suspend fun prepareMarkersOnMaps(
		activity: FragmentActivity?,
		context: Context?,
		markers: List<PlaceLocationPoint>
	) {
		map?.let { googleMap ->
			if (markers.isNotEmpty())
				throw(NotImplementedError())
		}
	}

	override fun updateMapZoomAndCenterPoint(cameraUpdate: CameraUpdate) {
		map?.moveCamera(cameraUpdate)
	}

	override fun moveMapTo(center: LatLng) {
		map?.moveCamera(center)
	}

	override fun clearAllObjectsOnMap() {
		clusterManagers.forEach {
			it.clearItems()
		}
		clusterManagers.clear()
		map?.clear()
	}
}