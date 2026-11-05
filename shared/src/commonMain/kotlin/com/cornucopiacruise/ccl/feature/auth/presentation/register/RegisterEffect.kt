package com.cornucopiacruise.ccl.feature.auth.presentation.register

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError

sealed interface RegisterEffect {
    data object NavigateToLogin : RegisterEffect
    data object NavigateToHome : RegisterEffect
    data class ShowAuthError(val error: AuthError) : RegisterEffect
}