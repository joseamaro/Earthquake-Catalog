package com.pro.earthquakecatalog.detail.data.repository

import com.pro.earthquakecatalog.detail.data.remote.DetailEarthquakeApi
import com.pro.earthquakecatalog.detail.data.remote.mapper.toDomain
import com.pro.earthquakecatalog.detail.domain.model.Detail
import com.pro.earthquakecatalog.utils.resultOf
import javax.inject.Inject

class DetailEarthquakeRepositoryImp @Inject constructor(
    private val detailEarthquakeApi: DetailEarthquakeApi
) : DetailEarthquakeRepository {
    override suspend fun getDetailEarthquake(id: String): Result<Detail> {
        return resultOf {
            detailEarthquakeApi.getDetailEarthquake(id = id).toDomain()
        }
    }
}