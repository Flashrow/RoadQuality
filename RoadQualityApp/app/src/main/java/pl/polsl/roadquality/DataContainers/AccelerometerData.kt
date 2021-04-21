package pl.polsl.roadquality

import java.util.*

data class AccelerometerData (
        val x : Double,
        val y : Double,
        val z : Double){
    var time : Long = System.currentTimeMillis()
}

enum class RoadFailure {HOLE, BUMP, ODDS}