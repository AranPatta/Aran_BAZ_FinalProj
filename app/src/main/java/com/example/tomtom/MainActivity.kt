package com.example.tomtom

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapFragment
import android.Manifest
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.map.display.camera.CameraOptions


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapOptions = MapOptions(mapKey = "AOYMhs1HWBhlfnU4mIaiSULFfvNGTw4Z")
        val mapFragment = MapFragment.newInstance(mapOptions)


        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            // Permission already granted
            initializeMapWithLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                initializeMapWithLocation()
            } else {
                Log.d("Permission", "No GPS")
            }
        }
    }

    private fun initializeMapWithLocation() {
        // Your existing code to initialize the map and add the MapFragment
        val mapOptions = MapOptions(mapKey = "AOYMhs1HWBhlfnU4mIaiSULFfvNGTw4Z")
        val mapFragment = MapFragment.newInstance(mapOptions)

        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment?.getMapAsync { tomtomMap ->
            // Assuming you've already requested location permissions and they've been granted
//            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
//                    location?.let {
//                        val mapLocationProvider = tomtomMap.getLocationProvider()
//                        val isLocationInVisibleArea = tomtomMap.isCurrentLocationInMapBoundingBox
//                        val currentLocation: GeoLocation? = tomtomMap.currentLocation
//
//                        tomtomMap.setLocationProvider(mapLocationProvider)
//
//                        val cameraOptions = CameraOptions(
//                            position = GeoPoint(42.349941064540054, -71.10323640274893),
//                            zoom = 10.0,
//                            tilt = 45.0,
//                            rotation = 90.0
//                        )
//                        tomtomMap.moveCamera(cameraOptions)
//
//                    }
//                }
//            }

            //tomtomMap.setLocationProvider(locationProvider)

            val mapLocationProvider = tomtomMap.getLocationProvider()
            val isLocationInVisibleArea = tomtomMap.isCurrentLocationInMapBoundingBox

            val currentLocation: GeoLocation? = tomtomMap.currentLocation

            Log.d("POS", currentLocation?.position.toString())

            val cameraOptions = CameraOptions(
                position = GeoPoint(42.34997406716152, -71.1032172645369),
                zoom = 17.0,
                tilt = 0.0,
                rotation = 0.0
            )
            tomtomMap.moveCamera(cameraOptions)

        }
    }
}