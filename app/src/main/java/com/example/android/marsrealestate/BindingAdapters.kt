package com.example.android.marsrealestate

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

/**
 * Create Binding Adapters
 * Use data binding with binding adapters to initialize the adapter with the list and
 * set the data in your views in the ViewHolders.
 * Add a binding adapter for the status ImageView, that will show a different image, based on
 * the status of the API request.
 */

@BindingAdapter("listMarsData")
fun setRecyclerViewMarsItems(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageUrl")
fun setMarsImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

@BindingAdapter("marsApiStatus")
fun bindMarsApiStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}

/**
 * Add a binding adapter for the property type TextView in the Details fragment, that will show
 * a different text "For Sale" or "For Rent", based on the property type. */
@BindingAdapter("propertyType")
fun displayMarsPropertyType(typeTextView: TextView, isRental: Boolean) {

    // Get the resources from the TextView
    val resources = typeTextView.resources
    val rentTypeString = resources.getString(R.string.type_rent)
    val saleTypeString = resources.getString(R.string.type_sale)

    when (isRental) {
        true -> typeTextView.text = resources.getString(R.string.display_type, rentTypeString)
        false -> typeTextView.text = resources.getString(R.string.display_type, saleTypeString)
    }
}

/**
 * Add a binding adapter for the property price TextView in the Details fragment, that will show
 * a different text "$500.000" or "$500.000/month", based on the property type. */
@BindingAdapter("propertyPrice")
fun displayMarsPropertyPrice(priceValueText: TextView, marsProperty: MarsProperty) {

    // Get the resources from the TextView
    val resources = priceValueText.resources

    when (marsProperty.type == "rent") {
        true -> priceValueText.text = resources
                .getString(R.string.display_price_monthly_rental, marsProperty.price)
        false -> priceValueText.text = resources
                .getString(R.string.display_price, marsProperty.price)
    }
}