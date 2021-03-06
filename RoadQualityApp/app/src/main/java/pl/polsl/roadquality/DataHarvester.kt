package pl.polsl.roadquality

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import pl.polsl.roadquality.DataContainers.DataRow
import pl.polsl.roadquality.DataContainers.GpsLocationData
import pl.polsl.roadquality.DataContainers.GyroscopeData
import java.io.File
import java.io.FileWriter


class DataHarvester(private val context: Context){
    var rows: MutableList<DataRow> = mutableListOf<DataRow>()

    var gpsDataList: MutableList<GpsLocationData> = mutableListOf()
    var accDataList: MutableList<AccelerometerData> = mutableListOf()
    var gyroscopeDataList: MutableList<GyroscopeData> = mutableListOf()

    var gpsData: GpsLocationData = GpsLocationData(0.0,0.0,0f)
    var accData: AccelerometerData = AccelerometerData(0.0,0.0,0.0)
    var gyroscopeData: GyroscopeData = GyroscopeData(0.0,0.0,0.0)

    var isRunning: Boolean = false
    var roadFailure: RoadFailure? = null
    var fileId: String = ""



    private val mainActivity: Activity = context as Activity

    val queue = Volley.newRequestQueue(mainActivity)

    public fun addGyroscopeData(data: GyroscopeData) {
        if(!isRunning) return
        gyroscopeDataList.add(data)
        gyroscopeData = data
        newGyroscopeDataAdded()
    }

    public fun addAccData(data: AccelerometerData) {
        if(!isRunning) return
        accDataList.add(data)
        accData = data
        newAccDataAdded()
    }

    public fun addGpsData(data: GpsLocationData) {
        if(!isRunning) return
        gpsData = data
        gpsDataList.add(data)
    }

    private fun newAccDataAdded(){
        var row: DataRow = DataRow(null, null, null, null)

        var gps = GpsLocationData(0.0, 0.0, 0f)
        if(gpsDataList.isNotEmpty()) gps = gpsDataList.last()

        var gyroscope = GyroscopeData(0.0, 0.0, 0.0)
        if(gyroscopeDataList.isNotEmpty() && System.currentTimeMillis() - gyroscopeDataList.last().time < 50 )
            gyroscope = gyroscopeDataList.last()

        row.gpsLocation = gps
        row.sensorData = accDataList.last()
        row.gyroscope = gyroscope

        rows.add(row)
        checkData()
    }

    private fun newGyroscopeDataAdded(){
        var row: DataRow = DataRow(null, null, null, null)

        var gps = GpsLocationData(0.0, 0.0, 0f)
        if(gpsDataList.isNotEmpty()) gps = gpsDataList.last()

        var acc = AccelerometerData(0.0, 0.0, 0.0)
        if(accDataList.isNotEmpty() && System.currentTimeMillis() - accDataList.last().time < 50 )
            acc = accDataList.last()

        row.gpsLocation = gps
        row.gyroscope = gyroscopeDataList.last()
        row.sensorData = acc

        rows.add(row)
        checkData()
    }

    public fun runRoadChecking(){
        var row: DataRow = DataRow(null, null, null, null)

    }

    private fun checkData(){
        if(rows.isNullOrEmpty() || rows.size < 2)
            return

        if(roadFailure != null) {
            rows.last().roadFailure = roadFailure
            roadFailure = null
        }
        //println("DataHarvester, now: " + System.currentTimeMillis() + ", last: " + rows.first().time + ", difference: " + (System.currentTimeMillis() - rows.first().time))
        if(System.currentTimeMillis() - rows.first().time > 5000 ){
            sendData()
        }

    }

    private fun sendData(){
        println("DataHarvester, sending data...")
        val fileName: String = fileId
        var csvBody: String = ""
        var json = ""
        val localFileWriter: FileWriter = openLocalCsvFile()

        openLocalCsvFile()
        for(row in rows) {
            var line = "${row.gpsLocation?.latitude};${row.gpsLocation?.longitude};${row.gpsLocation?.speed};"
            line += "${row.gyroscope?.x};${row.gyroscope?.y};${row.gyroscope?.z};"
            line += "${row.sensorData?.x};${row.sensorData?.y};${row.sensorData?.z};"
            line += "${row.roadFailure};"
            line += "${row.time};"

            localFileWriter.write(line)
            localFileWriter.write(String.format("%n"))
            
            line += "\\n"
            csvBody += line
        }
        localFileWriter.close()
        println("Rows $rows")
        json = "{\"id\":\"$fileName\", \"data\":\"$csvBody\"}"
        var jsonObj : JSONObject = JSONObject()

        try {
            jsonObj = JSONObject(json)
            Log.d("DataHarvester", " JSON have been parsed: $jsonObj")
        } catch (t: Throwable) {
            Log.e("DataHarvester", "Could not parse malformed JSON: \"$json\"")
        }

        val url = "http://roadquality.ddns.net/data"
        println("DataHarvester, Creating POST request...")
        val req = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            { response ->
                //println(response["msg"].toString())
                println("DataHarvester, JSON send via POST request")
            }, Response.ErrorListener { error: VolleyError ->
                println("DataHarvester, Error when sending $error.message")
            }
        )
        queue.add(req)
        rows.clear()
    }

    public fun openLocalCsvFile() : FileWriter{
        val externalStorageVolumes: Array<out File> =
                ContextCompat.getExternalFilesDirs(context, null)
        val primaryExternalStorage = externalStorageVolumes[0]
        val csvDataFile = File(primaryExternalStorage,"$fileId.csv")
        return FileWriter(csvDataFile, true)
    }

}