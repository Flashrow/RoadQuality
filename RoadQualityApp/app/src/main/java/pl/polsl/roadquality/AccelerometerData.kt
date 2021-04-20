package pl.polsl.roadquality

class AccelerometerData constructor() {
    var x: Double = 0.0
    var y: Double = 0.0
    var z: Double = 0.0
}

interface RowSensorData {
    val sensorData: AccelerometerData
    val roadFailure: RoadFailure
}

enum class RoadFailure {HOLE, BUMP, ODDS}