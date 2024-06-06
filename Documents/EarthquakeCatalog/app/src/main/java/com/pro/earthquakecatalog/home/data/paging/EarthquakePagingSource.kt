package com.pro.earthquakecatalog.home.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pro.earthquakecatalog.home.data.mapper.toDomain
import com.pro.earthquakecatalog.home.data.remote.HomeApi
import com.pro.earthquakecatalog.home.domain.model.EarthquakeFeature

class EarthquakePagingSource(
    private val homeApi: HomeApi,
    private val startTime: String,
    private val endTime: String,
    private val limit: Int
) : PagingSource<Int, EarthquakeFeature>() {

    override fun getRefreshKey(state: PagingState<Int, EarthquakeFeature>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EarthquakeFeature> {
        return try {
            val page = params.key ?: 1
            val earthquake = homeApi.getEarthquake(
                startTime = startTime,
                endTime = endTime,
                limit = limit,
                offset = page
            ).toDomain()

            LoadResult.Page(
                data = earthquake.features,
                prevKey = if (page == 1) null else page.minus(limit),
                nextKey = if (earthquake.features.isEmpty()) null else page.plus(limit)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}