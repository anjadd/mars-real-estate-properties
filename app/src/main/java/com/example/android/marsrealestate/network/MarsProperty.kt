package com.example.android.marsrealestate.network

import com.squareup.moshi.Json

/**
 * This data class defines a Mars property which includes an ID, the image URL, the type (sale
 * or rental) and the price (monthly if it's a rental).
 * */
data class MarsProperty(
        val id: String,
        val price: Double,
        val type: String,
        @Json(name = "img_src") val imgSrcUrl: String,
) {
    // Use a boolean to differentiate whether a property is for rent or not, based on the type
    val isRental = type == "rent"
}
