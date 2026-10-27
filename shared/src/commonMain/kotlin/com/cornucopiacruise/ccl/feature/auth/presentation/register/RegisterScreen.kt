package com.cornucopiacruise.ccl.feature.auth.presentation.register

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cornucopiacruise.ccl.core.presentation.theme.PreviewScaffold
import com.cornucopiacruise.ccl.feature.auth.domain.repository.StubAuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.messageRes
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is RegisterEffect.NavigateToHome -> onNavigateToHome()
                is RegisterEffect.NavigateToLogin -> onNavigateToLogin()
                is RegisterEffect.ShowRegisterError -> {
                    scope.launch {
                        val message = getString(effect.error.messageRes())
                        snackBarHostState.showSnackbar(message)
                    }
                }
            }
        }
    }

    RegisterUI(
        state = state.value,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent
    )
}

@Suppress("ViewModelConstructorInComposable")
@Preview
@Composable
private fun RegisterScreenPreview() {
    PreviewScaffold {
        RegisterScreen(
            viewModel = RegisterViewModel(StubAuthRepository()),
            onNavigateToLogin = {},
            onNavigateToHome = {}
        )
    }
}