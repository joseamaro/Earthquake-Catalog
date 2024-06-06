package com.pro.earthquakecatalog.authentication.register

data class RegisterState(
    val name: String = "",
    val lastName: String = "",
    val nameError: String? = null,
    val lastNameError: String? = null,
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val logIn: Boolean = false,
    val goToHome: Boolean = false
)