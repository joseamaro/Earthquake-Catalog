package com.pro.earthquakecatalog.detail.data.repository

import com.pro.earthquakecatalog.detail.domain.model.Detail

interface DetailEarthquakeRepository {
    suspend fun getDetailEarthquake(id: String) : Result<Detail>
}