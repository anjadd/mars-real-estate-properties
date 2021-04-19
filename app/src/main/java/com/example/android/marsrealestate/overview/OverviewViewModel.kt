package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    /* Internally, you should use a MutableLiveData, because you will be updating the List of
    *  MarsProperty with new values. */
    private val _marsProperties = MutableLiveData<List<MarsProperty>>()

    /* The external LiveData interface to the List of MarsProperty is immutable, so only this
   class can modify. */
    val marsProperties: LiveData<List<MarsProperty>>
        get() = _marsProperties

    /**
     * Call getMarsRealEstateProperties() on init so we can display the Mars properties immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    private fun getMarsRealEstateProperties() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getMarsProperties()
                // Update the response message for the successful response
                _marsProperties.value = MarsApi.retrofitService.getMarsProperties()
            } catch (e: Exception) {
                // Handle the failure response
                _marsProperties.value = ArrayList()
            }
        }
    }
}
