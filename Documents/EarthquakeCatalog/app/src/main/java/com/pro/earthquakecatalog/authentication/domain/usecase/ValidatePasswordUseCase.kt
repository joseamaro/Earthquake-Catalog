package com.pro.earthquakecatalog.authentication.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): PasswordResult {
        return when {
            password.isEmpty() -> {
                PasswordResult.INVALID_EMPTY
            }

            password.length < 8 -> {
                PasswordResult.INVALID_LENGTH
            }

            !password.any { it.isLowerCase() } -> {
                PasswordResult.INVALID_LOWERCASE
            }

            !password.any { it.isUpperCase() } -> {
                PasswordResult.INVALID_UPPERCASE
            }

            !password.any { it.isDigit() } -> {
                PasswordResult.INVALID_DIGITS
            }

            else -> {
                PasswordResult.VALID
            }

        }
    }
}

enum class PasswordResult {
    VALID,
    INVALID_LOWERCASE,
    INVALID_UPPERCASE,
    INVALID_DIGITS,
    INVALID_LENGTH,
    INVALID_EMPTY
}