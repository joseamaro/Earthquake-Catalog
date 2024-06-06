package com.pro.earthquakecatalog.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pro.earthquakecatalog.authentication.login.presentation.LoginScreen
import com.pro.earthquakecatalog.authentication.register.presentation.RegisterScreen
import com.pro.earthquakecatalog.detail.presentation.DetailEarthquakeScreen
import com.pro.earthquakecatalog.home.presentation.HomeScreen
import com.pro.earthquakecatalog.settings.presentation.SettingsScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute,
    onLogOut: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = startDestination.route) {
        composable(NavigationRoute.Login.route) {
            LoginScreen(onNavigateRegister = {
                navHostController.navigate(NavigationRoute.Register.route)
            }, onNavigateHome = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Home.route)
            })
        }
        composable(NavigationRoute.Register.route) {
            RegisterScreen(onBack = {
                navHostController.popBackStack()
            }, onNavigateHome = {
                navHostController.navigate(NavigationRoute.Home.route) {
                    popUpTo(navHostController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            })
        }
        composable(NavigationRoute.Home.route) {
            HomeScreen(onNavigateSettings = {
                navHostController.navigate(NavigationRoute.Settings.route)
            }, onNavigateDetail = {
                navHostController.navigate(NavigationRoute.Detail.route + "?earthquakeId=$it")
            })
        }
        composable(NavigationRoute.Settings.route) {
            SettingsScreen(onBack = {
                navHostController.popBackStack()
            }, onLogOut = {
                onLogOut()
                navHostController.navigate(NavigationRoute.Login.route) {
                    popUpTo(navHostController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            NavigationRoute.Detail.route + "?earthquakeId={earthquakeId}", arguments = listOf(
                navArgument(name = "earthquakeId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
        ) {
            DetailEarthquakeScreen(onBack = {
                navHostController.popBackStack()
            })
        }
    }
}