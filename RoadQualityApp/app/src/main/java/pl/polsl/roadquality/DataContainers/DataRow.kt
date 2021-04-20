package pl.polsl.roadquality.DataContainers

import pl.polsl.roadquality.AccelerometerData
import pl.polsl.roadquality.RoadFailure
import java.util.*

data class DataRow(
    val sensorData: AccelerometerData,
    val roadFailure: RoadFailure,
    val gpsLocation : GpsLocationData,
    val gyroscope : GyroscopeData
        ){
    var time : Date = Date()
}
