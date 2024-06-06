package com.pro.earthquakecatalog.detail.domain.usecase

import com.pro.earthquakecatalog.detail.data.repository.DetailEarthquakeRepository
import com.pro.earthquakecatalog.detail.domain.model.Detail
import javax.inject.Inject

class DetailEarthquakeUseCase @Inject constructor(
    private val detailEarthquake: DetailEarthquakeRepository
) {
    suspend operator fun invoke(id: String): Result<Detail> {
        return detailEarthquake.getDetailEarthquake(id)
    }
}