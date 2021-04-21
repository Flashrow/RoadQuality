package pl.polsl.roadquality.DataProviders

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import mumayank.com.airlocationlibrary.AirLocation
import pl.polsl.roadquality.DataContainers.GpsLocationData
import pl.polsl.roadquality.DataHarvester


class GpsLocationProvider(private val context: Context, private val dataHarvester: DataHarvester){
    private var longitude : Double = 0.0
    private var latitude : Double = 0.0
    private var speed : Double = 0.0

    private var previousLocation : Location? = null
    private val mainActivity : Activity = context as Activity

    private val airLocation = AirLocation(mainActivity, object : AirLocation.Callback {

        override fun onSuccess(locations: ArrayList<Location>) {
            // do something
            // the entire track is sent in locations
            //println("""Location Provider, last location: ${locations.last()}""")
            var gpsData: GpsLocationData = GpsLocationData(locations.last().latitude, locations.last().longitude, locations.last().speed)
            dataHarvester.addGpsData(gpsData)
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