package pl.polsl.roadqualit

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.ContextCompat.getSystemService
import pl.polsl.roadquality.AccelerometerData
import java.util.*


class AccelerometerManager(private val context: Context) : SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor

    public var ax : Double = 0.0
    public var ay : Double = 0.0
    public var az : Double = 0.0

    private var accData: AccelerometerData = AccelerometerData()

    public fun initializeManager(){
        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type ==Sensor.TYPE_ACCELEROMETER){
            ax= event?.values[0].toDouble()
            ay= event?.values[1].toDouble()
            az= event.values[2].toDouble()

            accData.x = event?.values[0].toDouble()
            accData.y = event?.values[1].toDouble()
            accData.z = event?.values[2].toDouble()

            println("Accelerometer reading, x:$ax, y:$ay, az:$az");
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

}