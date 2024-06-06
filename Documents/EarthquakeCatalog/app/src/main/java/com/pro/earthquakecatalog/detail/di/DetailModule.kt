package com.pro.earthquakecatalog.detail.di

import com.pro.earthquakecatalog.detail.data.remote.DetailEarthquakeApi
import com.pro.earthquakecatalog.detail.data.repository.DetailEarthquakeRepositoryImp
import com.pro.earthquakecatalog.detail.data.repository.DetailEarthquakeRepository
import com.pro.earthquakecatalog.utils.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DetailModule {

    @Singleton
    @Provides
    fun providerDetailApi(): DetailEarthquakeApi {
        return ApiServiceFactory.build(
            DetailEarthquakeApi::class.java,
            "https://earthquake.usgs.gov/fdsnws/"
        )
    }

    @Singleton
    @Provides
    fun providerDetailRepository(detailEarthquakeApi: DetailEarthquakeApi): DetailEarthquakeRepository {
        return DetailEarthquakeRepositoryImp(detailEarthquakeApi)
    }
}