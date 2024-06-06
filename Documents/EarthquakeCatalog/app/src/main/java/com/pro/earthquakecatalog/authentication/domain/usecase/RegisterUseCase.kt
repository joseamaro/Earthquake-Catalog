package com.pro.earthquakecatalog.authentication.domain.usecase

import com.pro.earthquakecatalog.authentication.data.repository.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String) : Result<Unit> {
        return authenticationRepository.registerUser(email = email, password = password)
    }
}