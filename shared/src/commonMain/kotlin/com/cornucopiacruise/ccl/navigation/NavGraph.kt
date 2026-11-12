package com.cornucopiacruise.ccl.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cornucopiacruise.ccl.feature.app.presentation.AppSessionState
import com.cornucopiacruise.ccl.feature.app.presentation.AppViewModel
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginScreen
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    appViewModel: AppViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val navigationManager = NavigationManager(navController)
    val sessionState = appViewModel.sessionState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when (sessionState.value) {
            AppSessionState.Loading -> AppRoute.Loader
            AppSessionState.SignedIn -> MainGraph
            AppSessionState.SignedOut -> AuthGraph
        }
    ) {
        composable<AppRoute.Loader> {
            Box(modifier = Modifier.fillMaxSize().background(Color.Red))
        }

        navigation<AuthGraph>(
            startDestination = AuthRoute.Register
        ) {
            composable<AuthRoute.Register> {
                RegisterScreen(
                    onNavigateToLogin = navigationManager.onNavigateToLogin(),
                    onNavigateToHome = navigationManager.onNavigateToHome()
                )
            }

            composable<AuthRoute.Login> {
                LoginScreen(
                    onNavigateToRegister = navigationManager.onNavigateToRegister(),
                    onNavigateToHome = navigationManager.onNavigateToHome()
                )
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

private class NavigationManager(
    private val navController: NavController
) {
    fun onNavigateToHome(): () -> Unit = {
        navController.navigate(MainGraph) {
            popUpTo<AuthGraph> {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun onNavigateToLogin(): () -> Unit = {
        navController.navigate(AuthRoute.Login)
    }

    fun onNavigateToRegister(): () -> Unit = {
        navController.popBackStack()
    }
}