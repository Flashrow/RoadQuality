package pl.polsl.roadquality.DataProviders

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat


class GpsLocationProvider(private val context: Context){
    private var longitude : Double = 0.0
    private var latitude : Double = 0.0
    private var speed : Double = 0.0

    private var previousLocation : Location? = null

    private lateinit var locationManager : LocationManager
    var provider : String = ""
    var criteria: Criteria? = null
    private lateinit var locationListener : LocationListener

    @SuppressLint("MissingPermission")
    fun initializeLocationService(){
        println("Location listener - creating location listener")
        locationListener = LocationListener() {
            fun onLocationChanged(location: Location) {
                println("Location Listener: $location")
            }

            fun onProviderDisabled(provider : String){
                println("Location Listener - provider disabled")
            }
        }

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, locationListener)
    }
}