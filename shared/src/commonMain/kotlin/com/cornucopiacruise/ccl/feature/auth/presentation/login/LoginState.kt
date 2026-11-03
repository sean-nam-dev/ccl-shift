package com.cornucopiacruise.ccl.feature.auth.presentation.login

data class LoginState(
    val email: String = "",
    val isEmailError: Boolean = false,
    val password: String = "",
    val isPasswordError: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false
)

val LoginState.hasErrors: Boolean
    get() = isEmailError || isPasswordError

fun LoginState.validated(): LoginState {
    return copy(
        isEmailError = email.isBlank(),
        isPasswordError = password.isBlank()
    )
}