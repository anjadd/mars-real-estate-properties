package com.example.android.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {

    private val _selectedMarsProperty = MutableLiveData<MarsProperty>()

    val selectedMarsProperty: LiveData<MarsProperty>
        get() = _selectedMarsProperty

    init {
        /* Get the selected property from the previous fragment */
        _selectedMarsProperty.value = marsProperty
    }
}
