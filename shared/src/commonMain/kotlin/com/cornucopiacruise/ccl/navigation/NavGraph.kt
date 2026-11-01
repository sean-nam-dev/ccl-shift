package com.cornucopiacruise.ccl.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cornucopiacruise.ccl.feature.auth.data.register.StubRegisterRepository
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterScreen
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<AuthGraph>(
            startDestination = AuthRoute.Register
        ) {
            composable<AuthRoute.Register> {
                RegisterScreen(
                    viewModel = RegisterViewModel(StubRegisterRepository()),
                    onNavigateToLogin = {
                        navController.navigate(AuthRoute.Login)
                    },
                    onNavigateToHome = {
                        navController.navigate(MainGraph) {
                            popUpTo<AuthGraph> {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<AuthRoute.Login> {
                Box(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                    Text("Login Screen")
                }
            }
        }

        navigation<MainGraph>(
            startDestination = MainRoute.Home
        ) {
            composable<MainRoute.Home> {
                Box(modifier = Modifier.fillMaxSize().background(Color.Green)) {
                    Text("Home Screen")
                }
            }
        }
    }
}