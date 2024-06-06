package com.pro.earthquakecatalog.authentication.di

import com.pro.earthquakecatalog.authentication.data.repository.AuthenticationRepository
import com.pro.earthquakecatalog.authentication.data.repository.AuthenticationRepositoryImp
import com.pro.earthquakecatalog.authentication.data.repository.ValidateEmailRepository
import com.pro.earthquakecatalog.authentication.data.repository.ValidateEmailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository() : AuthenticationRepository {
        return AuthenticationRepositoryImp()
    }

    @Provides
    @Singleton
    fun provideValidateEmailRepository() : ValidateEmailRepository {
        return ValidateEmailRepositoryImp()
    }
}