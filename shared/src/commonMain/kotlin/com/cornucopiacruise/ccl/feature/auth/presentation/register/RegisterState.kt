package com.cornucopiacruise.ccl.feature.auth.presentation.register

import com.cornucopiacruise.ccl.feature.auth.domain.validation.EmailError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.FullNameError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.PasswordError

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val fullNameError: FullNameError? = null,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val isLoading: Boolean = false
)