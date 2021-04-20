package pl.polsl.roadquality

import android.app.IntentService
import android.content.Context
import android.location.LocationManager
import pl.polsl.roadquality.DataContainers.DataRow
import pl.polsl.roadquality.DataProviders.GpsLocationProvider

class DataHarvester (private val context : Context){
    var listOfAccData: List<DataRow> = listOf<DataRow>()
    private lateinit var locationProvider : GpsLocationProvider

    init{
        println("Data Harvester, App context:$context")
        locationProvider = GpsLocationProvider(context)
        locationProvider.initializeLocationService()
    }

    public fun collectData(){

    }
}