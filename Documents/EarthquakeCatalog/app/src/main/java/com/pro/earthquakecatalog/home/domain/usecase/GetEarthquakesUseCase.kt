package com.pro.earthquakecatalog.home.domain.usecase

import androidx.paging.PagingData
import com.pro.earthquakecatalog.home.data.repository.HomeRepository
import com.pro.earthquakecatalog.home.domain.model.Earthquake
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEarthquakesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(
        startTime: String,
        endTime: String
    ): Flow<PagingData<EarthquakeFeature>> {
        return homeRepository.getEarthquake(
            startTime = startTime,
            endTime = endTime
        )
    }
}