package com.pro.earthquakecatalog.authentication.domain.usecase

import com.pro.earthquakecatalog.authentication.data.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke() : Boolean {
        return authenticationRepository.getUserId() != null
    }
}