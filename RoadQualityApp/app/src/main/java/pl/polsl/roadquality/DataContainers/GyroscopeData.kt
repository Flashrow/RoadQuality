package pl.polsl.roadquality.DataContainers

import java.util.*

data class GyroscopeData(
        val x : Double,
        val y : Double,
        val z : Double){
    var time : Date = Date()
}
