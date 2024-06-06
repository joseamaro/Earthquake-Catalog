package com.pro.earthquakecatalog.authentication.domain.usecase

import com.pro.earthquakecatalog.authentication.data.repository.ValidateEmailRepository
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val validateEmailRepository: ValidateEmailRepository
) {

    operator fun invoke(email: String): EmailResult {
        val isValid = validateEmailRepository.validateEmail(email)
        return when {
            email.isEmpty() -> {
                EmailResult.EMPTY
            }
            !isValid -> {
                EmailResult.INVALID
            }
            else -> {
                EmailResult.VALID
            }
        }
    }
}

enum class EmailResult {
    VALID, INVALID, EMPTY
}