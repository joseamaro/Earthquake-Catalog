package com.pro.earthquakecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pro.earthquakecatalog.navigate.NavigationHost
import com.pro.earthquakecatalog.navigate.NavigationRoute
import com.pro.earthquakecatalog.ui.theme.EarthquakeCatalogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EarthquakeCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationHost(
                        navHostController = navController,
                        startDestination = startDestination(),
                        onLogOut = {
                            mainViewModel.logOut()
                        }
                    )
                }
            }
        }
    }

    private fun startDestination() : NavigationRoute {
        return if (mainViewModel.isLoggedInState.value) {
            NavigationRoute.Home
        } else {
            NavigationRoute.Login
        }
    }
}