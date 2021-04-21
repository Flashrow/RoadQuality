package pl.polsl.roadquality.DataContainers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Calendar

object ActualDataRepository {

    private val _currentTime = MutableLiveData<String>()
    val currentTime : LiveData<String>
        get() = _currentTime

    init {
        _currentTime.value = Calendar.getInstance().time.toString()
    }
}