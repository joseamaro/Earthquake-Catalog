package com.pro.earthquakecatalog.home.di

import com.pro.earthquakecatalog.home.data.remote.HomeApi
import com.pro.earthquakecatalog.home.data.repository.HomeRepository
import com.pro.earthquakecatalog.home.data.repository.HomeRepositoryImp
import com.pro.earthquakecatalog.utils.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun providerHomeApi(): HomeApi {
        return ApiServiceFactory.build(
            HomeApi::class.java,
            "https://earthquake.usgs.gov/fdsnws/"
        )
    }

    @Singleton
    @Provides
    fun providerHomeRepository(homeApi: HomeApi): HomeRepository {
        return HomeRepositoryImp(homeApi)
    }
}