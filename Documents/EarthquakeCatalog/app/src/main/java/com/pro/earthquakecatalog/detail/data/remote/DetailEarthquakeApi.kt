package com.pro.earthquakecatalog.detail.data.remote

import com.pro.earthquakecatalog.detail.data.remote.response.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailEarthquakeApi {

    @GET("event/1/query")
    suspend fun getDetailEarthquake(
        @Query("format") format: String = "geojson",
        @Query("eventid") id: String
    ): DetailResponse
}