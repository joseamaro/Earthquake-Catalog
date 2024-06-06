package com.pro.earthquakecatalog.authentication.data.repository

import android.util.Patterns

class ValidateEmailRepositoryImp : ValidateEmailRepository {
    override fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}