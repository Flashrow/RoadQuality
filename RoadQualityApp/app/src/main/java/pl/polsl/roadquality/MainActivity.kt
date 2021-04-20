package pl.polsl.roadquality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import pl.polsl.roadqualit.AccelerometerManager

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

    val accelerometerManager : AccelerometerManager = AccelerometerManager(this)

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

        accelerometerManager.initializeManager()
    }
}