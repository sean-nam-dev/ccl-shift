package com.cornucopiacruise.ccl.feature.auth.presentation.login

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError

sealed interface LoginEffect {
    data object NavigateToRegister : LoginEffect
    data object NavigateToHome : LoginEffect
    data class ShowAuthError(val error: AuthError) : LoginEffect
}