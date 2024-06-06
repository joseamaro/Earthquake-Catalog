package com.pro.earthquakecatalog.authentication.util

import com.pro.earthquakecatalog.authentication.domain.usecase.PasswordResult

object PasswordErrorParser {

    fun parserError(password: PasswordResult) : String? {
        return when(password) {
            PasswordResult.VALID -> null
            PasswordResult.INVALID_LOWERCASE -> "Password must contain at least one lowercase letter"
            PasswordResult.INVALID_UPPERCASE -> "Password must contain at least one uppercase letter"
            PasswordResult.INVALID_DIGITS -> "Password must contain at least one number"
            PasswordResult.INVALID_LENGTH -> "Password must contain at least 8 characters"
            PasswordResult.INVALID_EMPTY -> "Password cannot be empty"
        }
    }
}