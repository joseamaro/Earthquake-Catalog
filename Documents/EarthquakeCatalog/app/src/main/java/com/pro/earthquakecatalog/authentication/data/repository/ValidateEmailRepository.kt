package com.pro.earthquakecatalog.authentication.data.repository

interface ValidateEmailRepository {
    fun validateEmail(email: String) : Boolean
}