package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

/* Enum representing all the available statuses of the Mars API web request */
enum class MarsApiStatus { LOADING, ERROR, DONE }

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

    /* LiveData in the view model to represent the status of the web request.
    There are three states to consider—loading, success, and failure.*/
    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() = _status

    /* Navigation for the Detail fragment.
    Create an event, whose changed value will trigger navigation to the DetailFragment. */
    private val _navigateToMarsPropertyDetails = MutableLiveData<MarsProperty?>()

    val navigateToMarsPropertyDetails: LiveData<MarsProperty?>
        get() = _navigateToMarsPropertyDetails


    /**
     * Call getMarsRealEstateProperties() on init so we can display the Mars properties immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved. Keep track of the status of the Mars API web request.
     * Add a query option to the web service request with Retrofit to include the filter of
     * properties.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                // Update the list of properties if the response is successful
                _marsProperties.value = MarsApi.retrofitService.getMarsProperties(filter.propertyType)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                /* Handle the failure response: set the status to error and clear the RecyclerView
                 * by setting the marsProperties to an empty list. */
                _marsProperties.value = ArrayList()
                _status.value = MarsApiStatus.ERROR
            }
        }
    }

    /* Call this method when the user selects an option from the overflow menu, and wants to filter
     the Mars properties by type: rent/buy/all, to update the web API request. */
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    /**
     * Create a function that sets _navigateToMarsPropertyDetails to the selected Mars property. */
    fun displayMarsPropertyDetails(marsProperty: MarsProperty) {
        _navigateToMarsPropertyDetails.value = marsProperty
    }

    fun onMarsPropertyDetailsNavigationDone() {
        _navigateToMarsPropertyDetails.value = null
    }
}
