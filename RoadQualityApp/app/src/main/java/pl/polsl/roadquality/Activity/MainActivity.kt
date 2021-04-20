package pl.polsl.roadquality.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import pl.polsl.roadquality.DataProviders.SensorManager
import pl.polsl.roadquality.R

class MainActivity : AppCompatActivity() {

    lateinit var btnStart : Button
    lateinit var btnStop : Button
    lateinit var btnHole : Button
    lateinit var btnOdds : Button
    lateinit var btnBump : Button

    lateinit var accelerometerVecLengthLabel : TextView
    lateinit var xAxisAccLabel : TextView
    lateinit var yAxisAccLabel : TextView
    lateinit var zAxisAccLabel : TextView
    lateinit var gpsPositionLabel : TextView
    lateinit var timeLabel : TextView

    private lateinit var sensorManager : SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnHole = findViewById(R.id.btnHole)
        btnOdds = findViewById(R.id.btnOdds)
        btnBump = findViewById(R.id.btnBump)
        
        accelerometerVecLengthLabel = findViewById(R.id.accVecLengthLabel)
        xAxisAccLabel = findViewById(R.id.xAxisAccLabel)
        yAxisAccLabel = findViewById(R.id.yAxisAccLabel)
        zAxisAccLabel = findViewById(R.id.zAxisAccLabel)
        gpsPositionLabel = findViewById(R.id.gpsLabel)
        timeLabel = findViewById(R.id.timeLabel)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            println("Location Service - no permission for access fine location")
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }
        sensorManager = SensorManager(this)
        sensorManager.initializeManager()
    }
}