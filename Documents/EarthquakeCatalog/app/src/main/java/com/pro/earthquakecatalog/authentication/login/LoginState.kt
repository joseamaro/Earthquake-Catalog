package com.pro.earthquakecatalog.authentication.login

data class LoginState (
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val loggedIn: Boolean = false,
    val signUp: Boolean = false,
    val loading: Boolean = false
)