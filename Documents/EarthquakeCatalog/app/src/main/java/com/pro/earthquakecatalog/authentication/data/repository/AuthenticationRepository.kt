package com.pro.earthquakecatalog.authentication.data.repository

interface AuthenticationRepository {

    suspend fun login(email: String, password: String) : Result<Unit>

    suspend fun registerUser(email: String, password: String) : Result<Unit>

    fun getUserId() : String?

    suspend fun logOut()
}