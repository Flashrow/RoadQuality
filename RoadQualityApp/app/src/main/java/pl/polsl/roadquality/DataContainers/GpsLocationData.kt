package pl.polsl.roadquality.DataContainers

import java.util.*

data class GpsLocationData(
        val latitude : Double,
        val longitude : Double,
        val speed : Double){
    var time : Date = Date()
}
