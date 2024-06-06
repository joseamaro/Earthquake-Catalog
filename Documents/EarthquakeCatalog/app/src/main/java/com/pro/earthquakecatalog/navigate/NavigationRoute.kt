package com.pro.earthquakecatalog.navigate

sealed class NavigationRoute(val route: String) {
    data object Login : NavigationRoute("login")
    data object Register : NavigationRoute("register")
    data object Home : NavigationRoute("home")
    data object Settings : NavigationRoute("settings")
    data object Detail: NavigationRoute("detail")
}