package com.cornucopiacruise.ccl.feature.auth.presentation.register.util

import com.cornucopiacruise.ccl.feature.auth.domain.validation.validateEmail
import com.cornucopiacruise.ccl.feature.auth.domain.validation.validateFullName
import com.cornucopiacruise.ccl.feature.auth.domain.validation.validatePassword
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterState

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
