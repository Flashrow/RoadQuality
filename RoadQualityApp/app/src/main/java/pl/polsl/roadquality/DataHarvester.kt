package pl.polsl.roadquality

import android.app.Activity
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pl.polsl.roadquality.DataContainers.DataRow
import pl.polsl.roadquality.DataContainers.GpsLocationData
import pl.polsl.roadquality.DataContainers.GyroscopeData

class DataHarvester (private val context: Context){
    var rows: List<DataRow> = listOf<DataRow>()

    var gpsDataList: List<GpsLocationData> = listOf()
    var accDataList: List<AccelerometerData> = listOf()
    var gyroscopeDataList: List<GyroscopeData> = listOf()

    private val mainActivity: Activity = context as Activity

    val queue = Volley.newRequestQueue(mainActivity)

    public fun addGyroscopeData(data: GyroscopeData) {
        gyroscopeDataList += data
        newGyroscopeDataAdded()
    }

    public fun addAccData(data: AccelerometerData) {
        accDataList += data
        newAccDataAdded()
    }

    public fun addGpsData(data: GpsLocationData) {
        gpsDataList += data
    }

    private fun newAccDataAdded(){
        var row: DataRow = DataRow(null, null, null, null)

        var gps = GpsLocationData(0.0,0.0,0f)
        if(gpsDataList.isNotEmpty()) gps = gpsDataList.last()

        var gyroscope = GyroscopeData(0.0,0.0,0.0)
        if(gyroscopeDataList.isNotEmpty() && System.currentTimeMillis() - gyroscopeDataList.last().time < 50 )
            gyroscope = gyroscopeDataList.last()

        row.gpsLocation = gps
        row.sensorData = accDataList.last()
        row.gyroscope = gyroscope

        rows += row
        checkData()
    }

    private fun newGyroscopeDataAdded(){
        var row: DataRow = DataRow(null, null, null, null)

        var gps = GpsLocationData(0.0,0.0,0f)
        if(gpsDataList.isNotEmpty()) gps = gpsDataList.last()

        var acc = AccelerometerData(0.0,0.0,0.0)
        if(accDataList.isNotEmpty() && System.currentTimeMillis() - accDataList.last().time < 50 )
            acc = accDataList.last()

        row.gpsLocation = gps
        row.gyroscope = gyroscopeDataList.last()
        row.sensorData = acc

        rows += row
        checkData()
    }

    private fun checkData(){
        if(rows.isNullOrEmpty() || rows.size < 2)
            return

        if(System.currentTimeMillis() - rows.first().time > 5000 )
            sendData()
    }

    private fun sendData(){
        val fileName: String = "NewFile"
        var csvBody: String = ""
        var json = ""
        for(row in rows) {
            csvBody += "${row.gpsLocation?.latitude};${row.gpsLocation?.longitude};${row.gpsLocation?.speed};"
            csvBody += "${row.gyroscope?.x};${row.gyroscope?.y};${row.gyroscope?.z};"
            csvBody += "${row.sensorData?.x};${row.sensorData?.y};${row.sensorData?.z};"
            csvBody += "${row.time};\n"
        }
        println("Rows $rows")

        json = "{\"id\":$fileName, \"data\":$csvBody}"
        val url = "http://roadquality.ddns.net/data"
/*
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            { response ->
                // Display the first 500 characters of the response string.
                println(response)
            },
            { println("API post error")}
        ){
            override fun getBody(): ByteArray {
                val parameters = HashMap<String, String>()
                parameters["key"] = "value"
                return JSONObject(parameters.toString()).toString().toByteArray()
            }
        }


        queue.add(stringRequest)
*/
        rows.dropLast(rows.size)
    }
}