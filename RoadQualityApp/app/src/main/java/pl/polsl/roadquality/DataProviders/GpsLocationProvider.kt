package pl.polsl.roadquality.DataProviders

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import mumayank.com.airlocationlibrary.AirLocation


class GpsLocationProvider(private val context: Context){
    private var longitude : Double = 0.0
    private var latitude : Double = 0.0
    private var speed : Double = 0.0

    private var previousLocation : Location? = null
    private val mainActivity : Activity = context as Activity

    private val airLocation = AirLocation(mainActivity, object : AirLocation.Callback {

        override fun onSuccess(locations: ArrayList<Location>) {
            // do something
            // the entire track is sent in locations
            println("""Location Provider, last location: ${locations.last()}""")
        }

        override fun onFailure(locationFailedEnum: AirLocation.LocationFailedEnum) {
            // do something
            // the reason for failure is given in locationFailedEnum
            println("Location Provider, failure: $locationFailedEnum")
        }

    })

    public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        airLocation.onActivityResult(requestCode, resultCode, data)
    }

    fun initializeLocationService(){
        airLocation.start()
    }
}