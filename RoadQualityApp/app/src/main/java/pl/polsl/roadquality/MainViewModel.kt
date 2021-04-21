package pl.polsl.roadquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.polsl.roadquality.DataContainers.ActualDataRepository

class MainViewModel : ViewModel() {

    var currentTime = MutableLiveData<String>()
    var currentPosition = MutableLiveData<String>()
    var accelerometerXaxis = MutableLiveData<String>()
    var accelerometerYaxis = MutableLiveData<String>()
    var accelerometerZaxis = MutableLiveData<String>()
    var accelerometerVecLenght = MutableLiveData<String>()

    var i : Int = 0

    fun onBumpClick(){
        i++
        currentTime.postValue(i.toString())
    }

    fun onOddsClick(){

    }

    fun onHoleClick(){

    }

    fun onStartClick(){

    }

    fun onStopClick(){

    }
}