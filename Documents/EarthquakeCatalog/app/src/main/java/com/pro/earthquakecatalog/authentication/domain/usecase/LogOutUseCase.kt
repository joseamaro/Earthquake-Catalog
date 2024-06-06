package com.pro.earthquakecatalog.authentication.domain.usecase

import com.pro.earthquakecatalog.authentication.data.repository.AuthenticationRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke() {
        authenticationRepository.logOut()
    }
}