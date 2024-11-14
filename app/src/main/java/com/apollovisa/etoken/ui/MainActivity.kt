package com.apollovisa.etoken.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apollovisa.etoken.ui.screens.dashboard.DashboardScreen
import com.apollovisa.etoken.ui.screens.login.LogInScreen
import com.apollovisa.etoken.ui.screens.splash.SplashScreen
import com.apollovisa.etoken.ui.theme.ETokenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            ETokenTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = Route.Splash) {
                        composable<Route.Splash> {
                            SplashScreen(
                                actionAfterDelay = {
                                    navController.popBackStack()
                                    navController.navigate(Route.LogIn)
                                }
                            )
                        }

                        composable<Route.LogIn> {
                            LogInScreen(onLogInSuccessful = { navController.navigate(Route.Dashboard) })
                        }

                        composable<Route.Dashboard> {
                            DashboardScreen()
                        }
                    }
                }
            }
        }
    }
}
