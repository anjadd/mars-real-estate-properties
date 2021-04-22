package com.example.android.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * This data class defines a Mars property which includes an ID, the image URL, the type (sale
 * or rental) and the price (monthly if it's a rental).
 * Make the data class parcelable so you can pass its object between fragments and activities. */
@Parcelize
data class MarsProperty(
        val id: String,
        val price: Double,
        val type: String,
        @Json(name = "img_src") val imgSrcUrl: String,
) : Parcelable {
    // Use a boolean to differentiate whether a property is for rent or not, based on the type
    val isRental = type == "rent"
}
