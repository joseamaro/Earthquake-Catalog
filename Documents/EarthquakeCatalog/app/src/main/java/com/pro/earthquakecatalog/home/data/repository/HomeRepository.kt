package com.pro.earthquakecatalog.home.data.repository

import androidx.paging.PagingData
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getEarthquake(
        startTime: String,
        endTime: String
    ): Flow<PagingData<EarthquakeFeature>>
}