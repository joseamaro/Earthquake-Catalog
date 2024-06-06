package com.pro.earthquakecatalog.authentication.util

import com.pro.earthquakecatalog.authentication.domain.usecase.EmailResult

object EmailErrorParser {

    fun errorEmail(emailResult: EmailResult): String? {
        return when (emailResult) {
            EmailResult.VALID -> null
            EmailResult.INVALID -> "The email entered is invalid"
            EmailResult.EMPTY -> "The email cannot be empty"
        }
    }
}