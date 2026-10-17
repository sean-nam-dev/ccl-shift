package com.cornucopiacruise.ccl.feature.auth.presentation.register

import com.cornucopiacruise.ccl.feature.auth.domain.repository.RegisterError

sealed interface RegisterEffect {
    data object NavigateToLogin : RegisterEffect
    data object NavigateToHome : RegisterEffect
    data class ShowRegisterError(val error: RegisterError) : RegisterEffect
}