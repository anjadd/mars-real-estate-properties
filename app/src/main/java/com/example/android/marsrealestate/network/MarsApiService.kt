package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Constant for the base URL for the web service
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

/* Create an enum called MarsApiFilter to define constants that match the query
values the web service expects, when using the filter option in the Mars API. */
enum class MarsApiFilter(val propertyType: String){
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

/**
 * Build the Moshi object that Retrofit will be using, with the Moshi builder.
 * Add the Kotlin adapter for full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Build a Retrofit object, using the Retrofit builder.
 * Add a Moshi converter with your Moshi object, and the base url.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

/**
 * A public interface that exposes the [getMarsProperties] method.
 *
 * Create an interface that defines how Retrofit talks to the web server using HTTP requests.
 *
 * */
interface MarsApiService {

    /**
     * Get a list of all available real estate properties on Mars:
     * https://android-kotlin-fun-mars-server.appspot.com/realestate
     * */
    @GET("realestate")
    suspend fun getMarsProperties(@Query("filter") propertyType: String): List<MarsProperty>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
