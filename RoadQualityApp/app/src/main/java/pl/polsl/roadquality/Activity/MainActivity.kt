package pl.polsl.roadquality.Activity

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import pl.polsl.roadquality.DataHarvester
import pl.polsl.roadquality.DataProviders.SensorManager
import pl.polsl.roadquality.MainViewModel
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
        bindViews()

        /*
        val mainViewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewmodel = mainViewModel
        }

        mainViewModel.currentTime.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG)
        })
*/
        sensorManager = SensorManager(this)
        sensorManager.initializeManager()
    }

    private fun requestInternetConnectionPermission(){

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        sensorManager.requestResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        sensorManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun bindViews(){
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
    }
}