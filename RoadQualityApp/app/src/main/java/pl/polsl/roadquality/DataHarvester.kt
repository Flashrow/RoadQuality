package pl.polsl.roadquality

import android.app.IntentService
import android.content.Context
import android.location.LocationManager
import pl.polsl.roadquality.DataContainers.DataRow
import pl.polsl.roadquality.DataProviders.GpsLocationProvider

class DataHarvester (){
    var listOfAccData: List<DataRow> = listOf<DataRow>()

    public fun collectData(){

    }
}