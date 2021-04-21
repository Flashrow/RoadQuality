package pl.polsl.roadquality.Activity

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
//import androidx.lifecycle.ViewModelProviders
import pl.polsl.roadquality.DataHarvester
import pl.polsl.roadquality.DataProviders.SensorManager
import pl.polsl.roadquality.MainViewModel
import pl.polsl.roadquality.R
import pl.polsl.roadquality.RoadFailure
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    private lateinit var dataHarvester : DataHarvester


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
        btnStop.isEnabled = false
        btnStop.isClickable = false

        dataHarvester = DataHarvester(this)

        btnStart.setOnClickListener {
            dataHarvester.fileId = generateFileId()
            dataHarvester.isRunning = true
            btnStart.isEnabled = false
            btnStart.isClickable = false
            btnStop.isEnabled = true
            btnStop.isClickable = true
        }

        btnStop.setOnClickListener {
            dataHarvester.isRunning = false
            btnStop.isEnabled = false
            btnStop.isClickable = false
            btnStart.isEnabled = true
            btnStart.isClickable = true
        }

        btnBump.setOnClickListener {
            dataHarvester.roadFailure = RoadFailure.BUMP
        }

        btnHole.setOnClickListener {
            dataHarvester.roadFailure = RoadFailure.HOLE
        }

        btnOdds.setOnClickListener {
            dataHarvester.roadFailure = RoadFailure.ODDS
        }

        sensorManager = SensorManager(this, dataHarvester)
        sensorManager.initializeManager()
    }

    private fun requestInternetConnectionPermission(){

    }

    fun generateFileId(): String {
        var date = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
        var formattedDate = date.format(formatter)
        return "$formattedDate"
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