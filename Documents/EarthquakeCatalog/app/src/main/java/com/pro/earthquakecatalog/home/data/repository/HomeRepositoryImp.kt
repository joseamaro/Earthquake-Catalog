package com.pro.earthquakecatalog.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pro.earthquakecatalog.home.data.paging.EarthquakePagingSource
import com.pro.earthquakecatalog.home.data.remote.HomeApi
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImp @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {
    override fun getEarthquake(
        startTime: String,
        endTime: String
    ): Flow<PagingData<EarthquakeFeature>> {

        return Pager(
            config = PagingConfig(pageSize = LIMIT_ELEMENTS, prefetchDistance = 1),
            pagingSourceFactory = {
                EarthquakePagingSource(
                    homeApi,
                    startTime = startTime,
                    endTime = endTime,
                    limit = LIMIT_ELEMENTS
                )
            }
        ).flow
    }

    companion object {
        private const val LIMIT_ELEMENTS = 20
    }
}