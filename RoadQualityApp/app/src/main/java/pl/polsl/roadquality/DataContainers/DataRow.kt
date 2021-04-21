package pl.polsl.roadquality.DataContainers

import pl.polsl.roadquality.AccelerometerData
import pl.polsl.roadquality.RoadFailure
import java.util.*

data class DataRow(
    var sensorData: AccelerometerData?,
    var roadFailure: RoadFailure?,
    var gpsLocation : GpsLocationData?,
    var gyroscope : GyroscopeData?
        ){
    var time : Long = System.currentTimeMillis()
}
