package com.cornucopiacruise.ccl.feature.auth.presentation.register

import com.cornucopiacruise.ccl.feature.auth.domain.validator.EmailError
import com.cornucopiacruise.ccl.feature.auth.domain.validator.FullNameError
import com.cornucopiacruise.ccl.feature.auth.domain.validator.PasswordError
import com.cornucopiacruise.ccl.feature.auth.domain.validator.validateEmail
import com.cornucopiacruise.ccl.feature.auth.domain.validator.validateFullName
import com.cornucopiacruise.ccl.feature.auth.domain.validator.validatePassword

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

val RegisterState.hasErrors: Boolean
    get() = fullNameError != null || emailError != null || passwordError != null

fun RegisterState.validated(): RegisterState {
    val trimmedFullName = fullName.trim()
    val trimmedEmail = email.trim()

    return copy(
        fullName = trimmedFullName,
        email = trimmedEmail,
        fullNameError = validateFullName(trimmedFullName),
        emailError = validateEmail(trimmedEmail),
        passwordError = validatePassword(password)
    )
}