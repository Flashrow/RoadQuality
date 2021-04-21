package pl.polsl.roadquality.DataProviders

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import pl.polsl.roadquality.AccelerometerData
import pl.polsl.roadquality.DataContainers.GpsLocationData
import pl.polsl.roadquality.DataContainers.GyroscopeData
import pl.polsl.roadquality.DataHarvester


class SensorManager(private val context: Context) : SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mGyroscope: Sensor

    private val dataHarvester : DataHarvester = DataHarvester(context)

    private lateinit var accData: AccelerometerData
    private lateinit var gyrosData: GyroscopeData
    private lateinit var locationProvider : GpsLocationProvider


    private var ax : Double = 0.0
    private var ay : Double = 0.0
    private var az : Double = 0.0

    private var gx : Double = 0.0
    private var gy : Double = 0.0
    private var gz : Double = 0.0

    public fun initializeManager(){
        locationProvider = GpsLocationProvider(context, dataHarvester)
        locationProvider.initializeLocationService()

        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type ==Sensor.TYPE_ACCELEROMETER){
            ax= event?.values[0].toDouble()
            ay= event?.values[1].toDouble()
            az= event.values[2].toDouble()

            accData = AccelerometerData(ax, ay, az)
            gyrosData = GyroscopeData(0.0,0.0,0.0)

            dataHarvester.addAccData(accData)

            //println("Accelerometer reading, x:$ax, y:$ay, az:$az");

        } else if(event?.sensor?.type ==Sensor.TYPE_GYROSCOPE) {
            gx = event?.values[0].toDouble()
            gy = event?.values[1].toDouble()
            gz = event?.values[2].toDouble()

            gyrosData = GyroscopeData(gx, gy, gz)
            accData = AccelerometerData(0.0, 0.0, 0.0)

            dataHarvester.addGyroscopeData(gyrosData)

            //println("Gyroscope reading, x:$gyrosX, y:$gyrosY, az:$gyrosZ");
        }
    }

    public fun requestResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        locationProvider.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        locationProvider.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

}