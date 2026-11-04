package com.cornucopiacruise.ccl.feature.auth.presentation.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.feature.auth.data.login.StubLoginRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome -> onNavigateToHome()
                is LoginEffect.NavigateToRegister -> onNavigateToRegister()
                is LoginEffect.ShowLoginError -> {
                    scope.launch {
                        val message = getString(effect.error.messageRes())
                        snackBarHostState.showSnackbar(message)
                    }
                }
            }
        }
    }

    LoginUI(
        state = state.value,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent
    )
}

@Preview
@Composable
@Suppress("ViewModelConstructorInComposable")
private fun LoginScreenPreview() {
    CCLShiftTheme {
        LoginScreen(
            viewModel = LoginViewModel(StubLoginRepository()),
            onNavigateToRegister = {},
            onNavigateToHome = {},
        )
    }
}