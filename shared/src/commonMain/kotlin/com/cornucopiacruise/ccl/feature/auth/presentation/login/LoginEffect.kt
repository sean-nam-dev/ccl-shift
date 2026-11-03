package com.cornucopiacruise.ccl.feature.auth.presentation.login

import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginError

sealed interface LoginEffect {
    data object NavigateToRegister : LoginEffect
    data object NavigateToHome : LoginEffect
    data class ShowLoginError(val error: LoginError) : LoginEffect
}