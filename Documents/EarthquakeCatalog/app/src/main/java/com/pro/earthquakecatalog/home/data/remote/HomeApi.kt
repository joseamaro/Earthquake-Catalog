package com.pro.earthquakecatalog.home.data.remote

import com.pro.earthquakecatalog.home.data.remote.response.EarthquakeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("event/1/query")
    suspend fun getEarthquake(
        @Query("format") format: String = "geojson",
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : EarthquakeResponse
}